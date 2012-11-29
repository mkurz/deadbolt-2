import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "deadbolt-scala"
  val appVersion      = "2.0-SNAPSHOT"

  val appDependencies = Seq(
    "be.objectify" %% "deadbolt-core" % "2.0-SNAPSHOT"
  )


  val main = play.Project(appName, appVersion, appDependencies).settings(
    organization := "be.objectify"
  )
}
