package org.drewfus.cre

import com.sksamuel.elastic4s.ElasticClient
import org.elasticsearch.common.settings.ImmutableSettings

object Crawl extends App {

  val crawler = CostarCrawler(ElasticClient.remote(ImmutableSettings.settingsBuilder()
			.put("cluster.name", "elasticsearch_drew")
			.build(), ("127.0.0.1", 9300)))
  crawler.crawl
  
}