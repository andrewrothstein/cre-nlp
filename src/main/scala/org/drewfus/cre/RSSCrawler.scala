package org.drewfus.cre

import akka.actor._

import com.sksamuel.elastic4s.ElasticClient
import com.sksamuel.elastic4s.ElasticDsl._
import com.sksamuel.elastic4s.source.ObjectSource

import dispatch._
import dispatch.Defaults._

import scala.concurrent.duration._

case class RSSCrawledItem(
  rssURL: String,
  author: String,
  pubDate: String,
  title: String,
  description: String,
  link: String)

case class RSSCrawlFailed(item: RSSCrawledItem)

case class RSSCrawlRequest(esClient: ElasticClient, rssURL: String)

class RSSCrawler extends Actor with ActorLogging {

  private def downloadAsXML(link: String) = Http(url(link) OK as.xml.Elem)

  private def downloadAsString(link: String) = Http(url(link) OK as.String)

  private def alreadyIndexedDoc(esClient: ElasticClient, link: String) = {
    val r = esClient.sync.execute {
      get id link from "cre/webpages"
    }
    r.isExists()
  }

  def receive = {
    case RSSCrawlRequest(esClient, rssURL) => {
      for (rss <- downloadAsXML(rssURL)) {

        val ttl = rss \ "channel" \ "ttl" text
        val parsedTTL = if (ttl.isEmpty()) 30 else Integer.parseInt(ttl)
        
        if (parsedTTL > 0) {
          log.info("waiting " + parsedTTL + " minutes for next ping against " + rssURL + "...")
          context.system.scheduler.scheduleOnce(parsedTTL minutes) {
	    	  self ! RSSCrawlRequest(esClient, rssURL)
	    	}
        }
        
        for (rssItem <- rss \\ "item") {

          val crawledItem = RSSCrawledItem(
            rssURL,
            rssItem \ "author" text,
            rssItem \ "pubDate" text,
            rssItem \ "title" text,
            rssItem \ "description" text,
            rssItem \ "link" text)

          if (!alreadyIndexedDoc(esClient, crawledItem.link)) {

            val dl = downloadAsString(crawledItem.link)
            for (doc <- dl) {
              val esResponse = esClient.bulk {
                index into "cre-rss-crawl-success" source ObjectSource(crawledItem)
                index into "cre/webpages" id crawledItem.link fields ("page" -> doc)
              }
              for (r <- esResponse) {
                log.info("downloaded and indexed " + crawledItem.title)
              }
              esResponse.onFailure {
                case _ => log.error("unable to index " + crawledItem)
              }
            }
            dl.onFailure {
              case _ =>
                esClient.execute {
                  index into "cre-rss-crawl-failure" source ObjectSource(crawledItem)
                }
                sender ! RSSCrawlFailed(crawledItem)
            }
          }
        }
      }
    }
  }
}