import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "deadbolt-usage-scala"
    val appVersion      = "1.1.3-SNAPSHOT"

    val appDependencies = Seq(
      "deadbolt-2" % "deadbolt-2_2.9.1" % "1.1.3-SNAPSHOT"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      // Change this to point to your local play repository
      resolvers += "Local Play Repository" at "file:///home/steve/development/play/play-2.0/repository/local"
    )

}
