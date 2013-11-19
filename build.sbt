name := "elastic4s-fiddle"

version := "0.1"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
 "com.sksamuel.elastic4s" % "elastic4s_2.10" % "0.90.5.2",
 "net.databinder.dispatch" %% "dispatch-core" % "0.11.0",
 "com.typesafe.akka" %% "akka-actor" % "2.2.3"
)
