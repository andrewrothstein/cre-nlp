package org.drewfus.elasticsearch.example

import com.sksamuel.elastic4s.ElasticClient
import com.sksamuel.elastic4s.ElasticDsl._
import org.elasticsearch.common.settings.ImmutableSettings
import scala.concurrent._
import scala.concurrent.duration._

object SimpleTextQuery extends App {

	val r = SimpleApp.client.sync.execute {
	  search in "cre" -> "webpages" query "JPMorgan" limit 2
	}
	println(r)
}