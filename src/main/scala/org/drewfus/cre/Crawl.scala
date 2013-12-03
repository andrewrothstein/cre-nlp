package org.drewfus.cre

import com.sksamuel.elastic4s.ElasticClient
import org.elasticsearch.common.settings.ImmutableSettings
import akka.actor._

object Crawl extends App {

  val esClient = ElasticClient.remote(
      ImmutableSettings.settingsBuilder()
      .put("cluster.name", "elasticsearch_drew")
      .build(), ("127.0.0.1", 9300));

  val locations = List("",
      "ATL", "AUS", "BLT", "BRM",
      "BOS", "CHA", "CHI", "CIN",
      "CLE", "COL", "XGR", "DFW",
      "DEN", "DET", "EBY", "GWS",
      "GVS", "HMP", "HAR", "HOU",
      "XPL", "IND", "RIV", "JAX",
      "KAN", "LVG", "SNY", "LAX",
      "MEM", "MIL", "MIN", "NSH",
      "NYC", "NNJ", "OKC", "LAO",
      "ORL", "PHI", "PHX", "PIT",
      "POR", "PRV", "RAL", "XRT",
      "RCH", "SAC", "SLC", "SAN",
      "SDO", "SFO", "SEA", "SBY",
      "SFL", "NAP", "STL", "TAM",
      "TUS", "TUL", "WAS", "XWL",
      "WCT", "GRP"
      )
  
  val system = ActorSystem("RSSCrawler")
  val actor = system.actorOf(Props[CostarCrawler])
  for (loc <- locations) {
    actor ! CostarCrawlRequest(esClient, loc)
  }
}