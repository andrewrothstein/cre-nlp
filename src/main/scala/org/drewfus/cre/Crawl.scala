package org.drewfus.cre

import com.sksamuel.elastic4s.ElasticClient
import org.elasticsearch.common.settings.ImmutableSettings
import akka.actor._

object Crawl extends App {

  case class Config(
      esclustername :String = "elasticsearch_drew",
      eshost :String = "127.0.0.1",
      esport :Int = 9300
      )
 
  val parser = new scopt.OptionParser[Config]("cre-rss-crawl") {
    head("cre-rss-crawl", "1.0")
    opt[String]("esclustername") action { (x, c) => c.copy(esclustername = x) } text("the elasticsearch cluster name")
    opt[String]("eshost") action { (x, c) => c.copy(eshost = x ) } text("the elasticsearch host name")
    opt[Int]("esport") action { (x, c) => c.copy(esport = x ) } text("the elastic search port")
  }

  parser.parse(args, Config()) map { config => { 
	  val esClient = ElasticClient.remote(
	      ImmutableSettings.settingsBuilder()
	      .put("cluster.name", config.esclustername)
	      .build(), (config.eshost, config.esport));
	
	  val system = ActorSystem("RSSCrawler")
	
	  val actor = system.actorOf(Props[RSSCrawler])
	  CostarCrawlRequests.crawlRequests(esClient).map(r => actor ! r)
  }}
}
