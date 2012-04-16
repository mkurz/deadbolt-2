import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "deadbolt-usage"
    val appVersion      = "1.1.2"

    val appDependencies = Seq(
      "deadbolt-2" %% "deadbolt-2" % "1.1.2"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
      // Change this to point to your local play repository
      resolvers += "Objectify Play Repository" at "http://schaloner.github.com/releases/"
    )

}
