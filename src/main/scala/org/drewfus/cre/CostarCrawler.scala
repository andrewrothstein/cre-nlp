package org.drewfus.cre

import akka.actor._

import com.sksamuel.elastic4s.ElasticClient
import com.sksamuel.elastic4s.ElasticDsl._
import com.sksamuel.elastic4s.source.ObjectSource

import dispatch._
import dispatch.Defaults._

case class CrawledItem(
  rssURL: String,
  author: String,
  pubDate: String,
  title: String,
  description: String,
  link: String)

case class CrawlFailed(item: CrawledItem)

case class CostarCrawlRequest(esClient: ElasticClient, rssURL: String)

class CostarCrawler extends Actor with ActorLogging {

  private def downloadAsXML(link: String) = Http(url(link) OK as.xml.Elem)

  private def downloadAsString(link: String) = Http(url(link) OK as.String)

  private def alreadyIndexedDoc(esClient: ElasticClient, link: String) = {
    val r = esClient.sync.execute {
      search in "cre" query {
        bool {
          must(term("link", link))
        }
      }
    }
    log.info("hits for " + link + ": " + r.getHits.getTotalHits)
    r.getHits.getTotalHits > 0
  }

  def receive = {
    case CostarCrawlRequest(esClient, rssURL) => {
      for (rss <- downloadAsXML(rssURL)) {
        for (rssItem <- rss \\ "item") {

          val crawledItem = CrawledItem(
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
                index into "costar-rss-success" source ObjectSource(crawledItem)
                index into "cre" fields ("link" -> crawledItem.link, "page" -> doc)
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
                  index into "costart-rss-failures" source ObjectSource(crawledItem)
                }
                sender ! CrawlFailed(crawledItem)
            }
          } else {
            log.info("skipping " + crawledItem.link)
          }
        }
      }
    }
  }
}