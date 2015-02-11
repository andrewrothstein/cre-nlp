name := "elastic4s-fiddle"

version := "0.1"

resolvers ++= Seq(
 "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
 "Sonatype Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"
)

libraryDependencies ++= Seq(
 "com.github.scopt" %% "scopt" % "3.3.0"
 , "com.sksamuel.elastic4s" %% "elastic4s" % "1.4.11"
 , "com.fasterxml.jackson.core" % "jackson-core" % "2.4.2"
 , "com.fasterxml.jackson.core" % "jackson-databind" % "2.4.2"
 , "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.4.2"
 , "net.databinder.dispatch" %% "dispatch-core" % "0.11.2"
 , "com.typesafe.akka" %% "akka-actor" % "2.3.9"
// , "org.apache.tika" % "tika-parsers" % "1.5"
 , "edu.stanford.nlp" % "stanford-corenlp" % "3.3.0"
 , "edu.stanford.nlp" % "stanford-corenlp" % "3.3.0" classifier "models"
)

