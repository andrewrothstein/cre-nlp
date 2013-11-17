package org.drewfus.cre

import dispatch._
import dispatch.Defaults._
import com.sksamuel.elastic4s.ElasticClient


case class CostarCrawler(client: ElasticClient) {

  def crawl = {
	  val rssFeed = url("http://www.costar.com/News/RSS/RSS.aspx")
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