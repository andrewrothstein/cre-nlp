package org.drewfus.cre

import com.sksamuel.elastic4s.ElasticClient
import org.elasticsearch.common.settings.ImmutableSettings
import akka.actor._

object Crawl extends App {

  val esClient = ElasticClient.remote(
      ImmutableSettings.settingsBuilder()
      .put("cluster.name", "elasticsearch_drew")
      .build(), ("127.0.0.1", 9300));

  val system = ActorSystem("RSSCrawler")

  val actor = system.actorOf(Props[RSSCrawler])
//  CatylistCrawlRequests.crawlRequests(esClient).map(r => actor ! r)
  CostarCrawlRequests.crawlRequests(esClient).map(r => actor ! r)
}