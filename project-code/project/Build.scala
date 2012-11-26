import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

    val appName         = "deadbolt-2"
    val appVersion      = "1.1.4-SNAPSHOT"

    val appDependencies = Seq(
    )

    val main = play.Project(appName, appVersion, appDependencies).settings(
      organization := "be.objectify"
    )
}
