import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "deadbolt-java"
  val appVersion      = "2.0-SNAPSHOT"

  val appDependencies = Seq(
    javaCore,
    "be.objectify" %% "deadbolt-core" % "2.0-SNAPSHOT"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    organization := "be.objectify"
  )
}
