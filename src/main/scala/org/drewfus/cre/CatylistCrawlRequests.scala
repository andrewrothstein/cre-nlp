package org.drewfus.cre

import com.sksamuel.elastic4s.ElasticClient

object CatylistCrawlRequests {

  private def urlsToCrawl = List(
      "http://feeds.feedburner.com/commercialiq-national-news?format=xml",
      "http://feeds.feedburner.com/commercialiq-new-listings?format=xml")
  
  def crawlRequests(esClient :ElasticClient) = urlsToCrawl.map(url => RSSCrawlRequest(esClient, url))
  
}