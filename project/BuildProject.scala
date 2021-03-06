import play.routes.compiler.InjectedRoutesGenerator
import play.sbt.PlayImport._
import play.sbt.PlayScala
import play.sbt.routes.RoutesKeys._
import sbt.Keys._
import sbt._

object BuildProject extends Build {
  lazy val id = "play-travel" // play-silhouette-jwt-restful-seed

  lazy val commonSettings = Seq(
    name := id,
    version := "0.0.1",
    organization := "com.play-travel",
    scalaVersion := "2.11.7",
    publishMavenStyle := true
  )

  lazy val root = Project(id = id, base = file("."))
    .settings(commonSettings: _*)
    .settings(
      sourcesInBase := false,
      resolvers ++= Seq(
        "Atlassian Releases" at "https://maven.atlassian.com/public/",
        "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases",
        Resolver.sonatypeRepo("snapshots"),
        Classpaths.typesafeReleases),
      libraryDependencies ++= Seq(
        "com.mohiva" %% "play-silhouette" % "3.0.0",
        "org.webjars" %% "webjars-play" % "2.4.0",
        "net.codingwell" %% "scala-guice" % "4.0.0",
        "net.ceedubs" %% "ficus" % "1.1.2",
        "com.adrianhurt" %% "play-bootstrap3" % "0.4.4-P24",
        "net.liftweb" % "lift-webkit_2.11" % "3.0-M6", // a light weight framework for web
        "com.google.guava" % "guava" % "18.0", // string process etc. (snake case for example)
        "com.mohiva" %% "play-silhouette-testkit" % "3.0.0" % "test",
        "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.0-RC1" % Test,
        "com.typesafe.play" %% "play-slick" % "1.0.1",
        "com.typesafe.play" %% "play-slick-evolutions" % "1.0.1",
        "mysql" % "mysql-connector-java" % "5.1.36",
        "com.github.tototoshi" %% "slick-joda-mapper" % "2.1.0",
        "joda-time" % "joda-time" % "2.7",
        "org.joda" % "joda-convert" % "1.7",
        specs2 % Test,
        cache,
        filters
      ),
      routesGenerator := InjectedRoutesGenerator,
      dependencyOverrides ++= Set(
        "commons-logging" % "commons-logging" % "1.2",
        "commons-io" % "commons-io" % "2.4",
        "org.scala-lang" % "scala-reflect" % "2.11.7",
        "org.scala-lang" % "scala-compiler" % "2.11.7",
        "org.scala-lang" % "scala-library" % "2.11.7",
        "org.scala-lang.modules" % "scala-xml_2.11" % "1.0.4",
        "org.scala-lang.modules" % "scala-parser-combinators_2.11" % "1.0.4",
        "org.apache.httpcomponents" % "httpclient" % "4.5.1",
        "org.apache.httpcomponents" % "httpcore" % "4.4.3",
        "com.google.guava" % "guava" % "18.0"
      )
    ).enablePlugins(PlayScala)

}

