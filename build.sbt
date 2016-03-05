name := "play-slick-advanced"

version := "2.0.0"

scalaVersion := "2.11.5"

crossScalaVersions := Seq("2.10.5", scalaVersion.value)

libraryDependencies ++= Seq(
  "org.virtuslab" %% "unicorn-play" % "0.6.2",
  "com.h2database" % "h2" % "1.4.181" % "test",
  "com.typesafe.play" %% "play-slick" % "0.8.1"
)

lazy val `play-slick-advanced` = (project in file(".")).enablePlugins(PlayScala, SbtWeb)


fork in run := false
