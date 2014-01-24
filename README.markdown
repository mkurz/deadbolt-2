# Deadbolt 2 - An authorisation system for Play 2 #

Deadbolt is a powerful authorisation mechanism for defining access rights to certain controller methods or parts of a view.

For a complete guide, please refer to the [deadbolt-2-guide](https://github.com/schaloner/deadbolt-2-guide).

The deadbolt-2 repository in GitHub is a collection of submodules.  I highly recommend that you fork/follow/clone specific submodules in place of this aggregate module.  The submodules are:

* deadbolt-2-core 
 * <https://github.com/schaloner/deadbolt-2-core>
* deadbolt-2-java
 * <https://github.com/schaloner/deadbolt-2-java>
* deadbolt-2-scala
 * <https://github.com/schaloner/deadbolt-2-scala>
* deadbolt-2-guide
 * <https://github.com/schaloner/deadbolt-2-guide>

Demonstration applications can be found at
 
* deadbolt-2-java-examples
 *  <https://github.com/schaloner/deadbolt-2-java-examples>
 * see it in action at <http://deadbolt-2-java.herokuapp.com>
* deadbolt-2-scala-examples
 * <https://github.com/schaloner/deadbolt-2-scala-examples>
 * see it in action at <http://deadbolt-2-scala.herokuapp.com>

## Which version should I use? ##
The architecture and versioning nomenclature of Deadbolt 2 changed for version 2.1 in order to clean up and normalise the code.  As a result, there are incompatibilities between the versions for Play 2.0 and Play 2.1.  

Firstly, you need to add the Objectify repository to your Build.scala file so the Deadbolt jars can be resolved

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
      resolvers += Resolver.url("Objectify Play Repository", url("http://schaloner.github.com/releases/"))(Resolver.ivyStylePatterns),
      resolvers += Resolver.url("Objectify Play Snapshot Repository", url("http://schaloner.github.com/snapshots/"))(Resolver.ivyStylePatterns)
    )

If you're using Play 2.0, don't forget to change the mainLang as necessary!

### Play 2.0 ###
If you're using Play 2.0, add the following to your Build.scala

    val appDependencies = Seq(
      "be.objectify" %% "deadbolt-2" % "1.1.3"
    )

If you're using Scala, I *strongly* recommend you use Deadbolt 2.1.  Versions before this had very poor Scala support.  The 2.1 re-design was partially aimed at correcting this.

### Play 2.1 ###
For Play 2.1, you need to use the Deadbolt 2.1 in your project. Add one or both of the following to Build.scala according to your requirements

    val appDependencies = Seq(
      "be.objectify" %% "deadbolt-java" % "2.1.2",
      "be.objectify" %% "deadbolt-scala" % "2.1"
    )


### Play 2.2 ###
For Play 2.2, you need to use the Deadbolt 2.2 in your project. Add one or both of the following to Build.scala according to your requirements

    val appDependencies = Seq(
      "be.objectify" %% "deadbolt-java" % "2.2-RC4",
      "be.objectify" %% "deadbolt-scala" % "2.2-RC2"
    )



