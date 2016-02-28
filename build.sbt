import sbt.Keys._

name := "finatra-sangria-elastic4s-example"

version := "1.0"

scalaVersion := "2.11.7"

parallelExecution in ThisBuild := true

mainClass in Compile := Some("com.logicalguess.ItemServerMain")

assemblyJarName in assembly := "api-finatra-graphql.jar"

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

lazy val versions = new {
  val finatra = "2.1.2"
  val mustache = "0.9.1"
  val logback = "1.0.13"
  val guice = "4.0"
  val mockito = "1.9.5"
  val elastic4s = "2.2.0"
  val elastic4sjackson = "2.2.0"
  val sangria = "0.4.3"
  var json4s = "3.2.11"
  val scalatest = "2.2.3"
  val specs2 = "2.3.12"
}

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  "Twitter Maven" at "https://maven.twttr.com",
  "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/",
  Resolver.url("scala sbt",  url("http://repo.scala-sbt.org/scalasbt/sbt-plugin-releases"))(Resolver.ivyStylePatterns),
  Resolver.url("typesafe ivy",  url("http://repo.typesafe.com/typesafe/ivy-releases"))(Resolver.ivyStylePatterns)
)

libraryDependencies ++= Seq(
  "com.twitter.finatra" %% "finatra-http" % versions.finatra,
  "com.twitter.finatra" %% "finatra-httpclient" % versions.finatra,
  "com.twitter.finatra" %% "finatra-slf4j" % versions.finatra,
  "com.twitter.inject" %% "inject-core" % versions.finatra,
  "com.github.spullara.mustache.java" % "compiler" % versions.mustache,
  "ch.qos.logback" % "logback-classic" % versions.logback,
  "com.sksamuel.elastic4s" %% "elastic4s-core" % versions.elastic4s,
  "com.sksamuel.elastic4s" %% "elastic4s-jackson" % versions.elastic4sjackson,
  "org.json4s" %% "json4s-native" % versions.json4s,
  "org.sangria-graphql" %% "sangria" % versions.sangria,

  "org.apache.spark" %% "spark-core" % "1.4.0",
  "org.apache.spark" %% "spark-mllib" % "1.4.0",
  "org.apache.mahout" % "mahout-core" % "0.9",


"com.twitter.finatra" %% "finatra-http" % versions.finatra % "test",
  "com.twitter.finatra" %% "finatra-jackson" % versions.finatra % "test",
  "com.twitter.inject" %% "inject-server" % versions.finatra % "test",
  "com.twitter.inject" %% "inject-app" % versions.finatra % "test",
  "com.twitter.inject" %% "inject-core" % versions.finatra % "test",
  "com.twitter.inject" %% "inject-modules" % versions.finatra % "test",
  "com.google.inject.extensions" % "guice-testlib" % versions.guice % "test",
  "com.twitter.finatra" %% "finatra-http" % versions.finatra % "test" classifier "tests",
  "com.twitter.inject" %% "inject-server" % versions.finatra % "test" classifier "tests",
  "com.twitter.inject" %% "inject-app" % versions.finatra % "test" classifier "tests",
  "com.twitter.inject" %% "inject-core" % versions.finatra % "test" classifier "tests",
  "com.twitter.inject" %% "inject-modules" % versions.finatra % "test" classifier "tests",
  "org.mockito" % "mockito-core" % versions.mockito % "test",
  "org.scalatest" %% "scalatest" % versions.scalatest % "test",
  "org.specs2" %% "specs2" % versions.specs2 % "test"
).map(
  _.excludeAll(ExclusionRule(organization = "org.mortbay.jetty"))
)