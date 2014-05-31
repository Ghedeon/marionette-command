import sbt._
import Keys._

object build extends Build {
  val root = Project(
    id = "marionette-command",
    base = file("."),
    settings = Defaults.defaultSettings ++ Seq(
      name := "marionette-command",
      version := "1.0-SNAPSHOT",
      organization := "co.techsylvania",
      scalaVersion := "2.11.1",

      resolvers ++= Seq(
        "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
	"gideondk-repo" at "https://raw.github.com/gideondk/gideondk-mvn-repo/master",
	"Mandubian Repository" at "https://github.com/mandubian/mandubian-mvn/raw/master/releases/",
        "Mandubian Repository Snapshots" at "https://github.com/mandubian/mandubian-mvn/raw/master/snapshots/"
      ),

      libraryDependencies ++= Seq(
        "org.slf4j" % "slf4j-api" % "1.7.5",
        "ch.qos.logback" % "logback-classic" % "1.0.13",
	"com.typesafe.akka" % "akka-actor_2.11" % "2.3.3",
	"nl.gideondk" %% "sentinel" % "0.7.4"
      )
    )
  )
}

