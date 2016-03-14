name := "play-slick-advanced"

version := "2.0.0"

scalaVersion := "2.11.6"

//crossScalaVersions := Seq("2.10.5", scalaVersion.value)

libraryDependencies ++= Seq(
  "org.virtuslab" %% "unicorn-play" % "0.6.2",
  //"com.h2database" % "h2" % "1.4.181" % "test",
  "mysql" % "mysql-connector-java" % "5.1.21",
  "com.typesafe.play" %% "play-slick" % "0.8.1"
  //"com.typesafe.play.modules" %% "play-modules-redis" % "2.4.0"
)

//resolvers += "google-sedis-fix" at "http://pk11-scratch.googlecode.com/svn/trunk"

lazy val `play-slick-advanced` = (project in file(".")).enablePlugins(PlayScala, SbtWeb)

lazy val root = (project in file(".")).enablePlugins(PlayScala)

fork in run := false
