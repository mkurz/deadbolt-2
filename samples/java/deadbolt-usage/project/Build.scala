import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "deadbolt-usage"
    val appVersion      = "1.0"

    val appDependencies = Seq(
      "be.objectify" % "deadbolt-2_2.9.1" % "1.0"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
      // Change this to point to your local play repository
      resolvers += "Local Play Repository" at "file:///home/steve/development/play/play-2.0/repository/local"
    )

}
