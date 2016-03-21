name := """play-travel"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"


//resolvers := ("Atlassian Releases" at "https://maven.atlassian.com/public/") +: resolvers.value

resolvers += "Atlassian Releases" at "https://maven.atlassian.com/public/"

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

resolvers += Resolver.sonatypeRepo("snapshots")

resolvers += Resolver.jcenterRepo

libraryDependencies ++= Seq(
  //jdbc,
  evolutions,
  filters,
  cache,
  ws,
  "com.mohiva" %% "play-silhouette" % "3.0.0",
  "net.codingwell" %% "scala-guice" % "4.0.0",
  "com.adrianhurt" %% "play-bootstrap3" % "0.4.4-P24",
  "com.iheart" %% "ficus" % "1.1.3",
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.0-RC1" % Test,
  "com.typesafe.play" %% "play-slick" % "1.1.1",
  "com.typesafe.play" %% "play-slick-evolutions" % "1.1.1",
  "mysql" % "mysql-connector-java" % "5.1.36"
)



