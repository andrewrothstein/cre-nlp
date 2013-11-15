package org.drewfus.elasticsearch.example

import com.sksamuel.elastic4s.ElasticClient
import com.sksamuel.elastic4s.ElasticDsl._
import org.elasticsearch.common.settings.ImmutableSettings
import scala.concurrent._
import scala.concurrent.duration._

object SimpleTextQuery extends App {
	val settings = ImmutableSettings.settingsBuilder()
			.put("cluster.name", "elasticsearch_drew")
			.build()
	val client = ElasticClient.remote(settings, ("127.0.0.1", 9300))
	val r = client.execute {
	  search in "logstash-2013.11.10" query "first"
	}
	Await.ready(r, 10 seconds)
	println(r.value)
}