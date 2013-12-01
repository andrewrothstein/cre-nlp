package org.drewfus.cre

import com.sksamuel.elastic4s.ElasticClient
import org.elasticsearch.common.settings.ImmutableSettings
import akka.actor._
import scala.concurrent.duration._

object Crawl extends App {

  val esClient = ElasticClient.remote(
      ImmutableSettings.settingsBuilder()
      .put("cluster.name", "elasticsearch_drew")
      .build(), ("127.0.0.1", 9300));

  val rssURL = "http://www.costar.com/News/RSS/RSS.aspx";
    
  val system = ActorSystem("Crawler")
  val actor = system.actorOf(Props[CostarCrawler])

  import system.dispatcher
  system.scheduler.schedule(0 milliseconds, 15 seconds, actor, CostarCrawlRequest(esClient, rssURL))
  
}