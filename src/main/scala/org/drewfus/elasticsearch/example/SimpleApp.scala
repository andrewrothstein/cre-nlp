package org.drewfus.elasticsearch.example

import org.elasticsearch.common.settings.ImmutableSettings
import com.sksamuel.elastic4s.ElasticClient

object SimpleApp extends App {
	lazy val client = ElasticClient.remote(ImmutableSettings.settingsBuilder()
			.put("cluster.name", "elasticsearch_drew")
			.build(), ("127.0.0.1", 9300))
}