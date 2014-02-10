name := "scaspell"

version := "1.0"

scalaVersion := "2.10.3"

scalacOptions ++= Seq(
    "-deprecation",
    "-encoding", "UTF-8",
    "-feature",
    "-target:jvm-1.7",
    "-language:implicitConversions",
    "-language:postfixOps",
    "-unchecked",
    "-Ywarn-adapted-args",
    "-Ywarn-value-discard",
    "-Xlint"
)

// set correct java version
javacOptions ++= Seq("-source", "1.7", "-target", "1.7")

// Resolver
resolvers ++= Seq(
    "webjars" at "http://webjars.github.com/m2",
    "Local Maven Repository" at "file:///" + Path.userHome.absolutePath + "/.m2/repo",
    Resolver.file("Local Repository", file(sys.env.get("PLAY_HOME").map(_ + "/repository/local").getOrElse("")))(Resolver.ivyStylePatterns),
    Resolver.url("play-plugin-releases", new URL("http://repo.scala-sbt.org/scalasbt/sbt-plugin-releases/"))(Resolver.ivyStylePatterns),
    Resolver.url("play-plugin-snapshots", new URL("http://repo.scala-sbt.org/scalasbt/sbt-plugin-snapshots/"))(Resolver.ivyStylePatterns)
)

publishMavenStyle := true

publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
        Some("snapshots" at nexus + "content/repositories/snapshots")
    else
        Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra := (
    <url>https://github.com/l0rdn1kk0n/scaspell</url>
    <licenses>
        <license>
            <name>The Apache Software License, Version 2.0</name>
            <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
            <distribution>repo</distribution>
        </license>
    </licenses>
    <scm>
        <url>git@github.com:l0rdn1kk0n/scaspell.git</url>
        <connection>scm:git:git@github.com:l0rdn1kk0n/scaspell.git</connection>
        <developerConnection>scm:git:git@github.com:l0rdn1kk0n/scaspell.git</developerConnection>
    </scm>
    <developers>
        <developer>
            <id>miha</id>
            <name>Michael Haitz</name>
            <url>http://agilecoders.de</url>
            <email>michael.haitz@agilecoders.de</email>
            <organization>agilecoders.de</organization>
            <roles>
                <role>Owner</role>
                <role>Comitter</role>
            </roles>
        </developer>
    </developers>
)

licenses := Seq("Apache v2.0" -> url("http://opensource.org/licenses/Apache-2.0"))

homepage := Some(url("https://github.com/l0rdn1kk0n/scaspell"))