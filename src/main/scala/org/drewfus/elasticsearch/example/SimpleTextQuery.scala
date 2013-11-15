package org.drewfus.elasticsearch.example

import com.sksamuel.elastic4s.ElasticClient
import com.sksamuel.elastic4s.ElasticDsl._
import org.elasticsearch.common.settings.ImmutableSettings
import scala.concurrent._
import scala.concurrent.duration._

object SimpleTextQuery extends App {

	val r = SimpleApp.client.execute {
//	  search in "logstash-2013.11.10" query "first"
	  search in "cre" query "the news"
	}
	Await.ready(r, 10 seconds)
	println(r.value)
}