import sbt._
import sbt.Keys._
import spray.revolver.RevolverPlugin._

object ScaspellRoot extends Build {

    val _version = "1.0.0-SNAPSHOT"

    lazy val root = Project(
        id = "scaspell-root",
        base = file("."),
        settings = Project.defaultSettings ++
                   Revolver.settings ++
                   Seq(
                       version := _version,
                       mainClass in Revolver.reStart := Some("de.agilecoders.projects.scaspell.Server")
                   )
    ).aggregate(client, api).dependsOn(service)

    lazy val service = Project(
        id = "scaspell-service",
        base = file("scaspell-service"),
        dependencies = Seq(api),

        settings = Project.defaultSettings ++ Seq(
            version := _version,
            libraryDependencies ++= Dependencies.testKit,
            libraryDependencies ++= Dependencies.core
        )
    )

    lazy val client = Project(
        id = "scaspell-client",
        base = file("scaspell-client"),
        dependencies = Seq(api),

        settings = Project.defaultSettings ++ Seq(
            version := _version,
            libraryDependencies ++= Dependencies.testKit,
            libraryDependencies ++= Dependencies.core
        )
    )

    lazy val api = Project(
        id = "scaspell-api",
        base = file("scaspell-api"),

        settings = Project.defaultSettings ++ Seq(
            version := _version,
            libraryDependencies ++= Seq(Dependencies.Runtime.twitterUtil)
        )
    )

    // Dependencies similar to the Akka project style of definition

    object Dependencies {

        object Runtime {
            val finagleHttp = "com.twitter" %% "finagle-http" % "6.2.0"
            val twitterUtil = "com.twitter" %% "util-core" % "6.2.0"
            val json = "io.spray" %% "spray-json" % "1.2.5"
        }

        object Test {
            val scalaTest = "org.scalatest" %% "scalatest" % "2.0" % "test"
            val mockito = "org.mockito" % "mockito-all" % "1.8.1" % "test"
        }

        val core = Seq(Runtime.finagleHttp, Runtime.json, Runtime.twitterUtil)
        val testKit = Seq(Test.scalaTest, Test.mockito)
    }

}