name := "elastic4s-fiddle"

version := "0.1"

resolvers ++= Seq(
 "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
 "Sonatype Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"
)

libraryDependencies ++= Seq(
"com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.1.3",
 "com.sksamuel.elastic4s" %% "elastic4s" % "1.2.1.0",
 "net.databinder.dispatch" %% "dispatch-core" % "0.11.0",
 "com.typesafe.akka" %% "akka-actor" % "2.3.3",
 "org.apache.tika" % "tika-parsers" % "1.5",
 "edu.stanford.nlp" % "stanford-corenlp" % "3.3.0",
 "edu.stanford.nlp" % "stanford-corenlp" % "3.3.0" classifier "models"
)
