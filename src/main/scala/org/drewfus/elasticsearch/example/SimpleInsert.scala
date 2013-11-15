package org.drewfus.elasticsearch.example

import com.sksamuel.elastic4s.ElasticDsl._
import scala.concurrent._
import scala.concurrent.duration._

object SimpleInsert extends App {
  
  val r = SimpleApp.client.execute {
// 	create index "cre"
 	index into "cre" fields ("id" -> 1, "text" -> "the news")
	}
  Await.ready(r, 10 seconds)
  println(r.value)
}
