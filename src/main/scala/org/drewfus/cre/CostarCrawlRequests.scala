package org.drewfus.cre

import com.sksamuel.elastic4s.ElasticClient

object CostarCrawlRequests {

  private def resolveRSSURL(loc :String) = { 
	val suffix = if (loc.isEmpty) "" else "?m=" + loc
	"http://www.costar.com/News/RSS/RSS.aspx" + suffix
  }
  
  private def crawlRequest(esClient :ElasticClient, loc :String) = RSSCrawlRequest(esClient, resolveRSSURL(loc))

  private val locations = List("",
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
    "WCT", "GRP")

  def crawlRequests(esClient: ElasticClient) = locations.map(l => crawlRequest(esClient, l)) 
}
