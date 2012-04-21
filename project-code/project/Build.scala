import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "deadbolt-2"
    val appVersion      = "1.1.3-SNAPSHOT"

    val appDependencies = Seq(
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
      organization := "be.objectify"
    )
}
