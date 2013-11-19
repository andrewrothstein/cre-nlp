package org.drewfus.cre

import dispatch._
import dispatch.Defaults._
import com.sksamuel.elastic4s.ElasticClient
import akka.actor._

case class CostarCrawlRequest(esClient: ElasticClient, rssURL :String)

class CostarCrawler extends Actor {

  def receive = {
    case CostarCrawlRequest(esClient, rssURL) =>
      val rssFeed = url(rssURL)
	  val rssResponse = Http(rssFeed OK as.xml.Elem)
	  for (rss <- rssResponse) yield {
	    for (i <- rss \\ "item") {
		    println(i)
		    val author = i \ "author"
		    val pubDate = i \ "pubDate"
		    val title = i \ "title"
		    val link = i \ "link"
		    val description = i \ "description"
		    println("author: " + author)
		    println("pubDate: " + pubDate)
		    println("title: " + title)
		    println("link: " + link)
		    println("description: " + description)
		    
		    val docUrl = url(link.text)
		    val docResponse = Http(docUrl OK as.String)
		    for (doc <- docResponse) {
		      println(doc)
		    }
		  }
	  }
	}
}