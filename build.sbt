name := "cerebro"

maintainer := "Leonardo Menezes <leonardo.menezes@xing.com>"

packageSummary := "Elasticsearch web admin tool"

packageDescription := """cerebro is an open source(MIT License) elasticsearch web admin tool built
  using Scala, Play Framework, AngularJS and Bootstrap."""

version := "0.9.5"

scalaVersion := "2.13.16"

rpmVendor := "lmenezes"

rpmLicense := Some("MIT")

rpmUrl := Some("http://github.com/lmenezes/cerebro")

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play"                    % "2.8.21",
  "com.typesafe.play" %% "play-json"               % "2.9.4",
  "com.typesafe.play" %% "play-slick"              % "5.0.0",
  "com.typesafe.play" %% "play-slick-evolutions"   % "5.0.0",
  "org.xerial"        %  "sqlite-jdbc"             % "3.41.2.2",

  "com.google.guava" % "guava" % "32.0.1-jre",
  "ch.qos.logback" % "logback-classic" % "1.2.13",
  "ch.qos.logback" % "logback-core" % "1.2.13",

  "com.fasterxml.jackson.core" % "jackson-core" % "2.15.4",
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.15.4",
  "com.fasterxml.jackson.core" % "jackson-annotations" % "2.15.4",
  "com.fasterxml.jackson.datatype" % "jackson-datatype-jdk8" % "2.15.4",
  "com.fasterxml.jackson.datatype" % "jackson-datatype-jsr310" % "2.15.4",
  "com.fasterxml.jackson.dataformat" % "jackson-dataformat-cbor" % "2.15.4",
  "com.fasterxml.jackson.module" % "jackson-module-parameter-names" % "2.15.4",
  "com.fasterxml.jackson.module" % "jackson-module-paranamer" % "2.15.4",
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.15.4",

  "org.specs2"        %% "specs2-junit"  % "4.10.0" % "test",
  "org.specs2"        %% "specs2-core"   % "4.10.0" % "test",
  "org.specs2"        %% "specs2-mock"   % "4.10.0" % "test"
)

libraryDependencies += filters
libraryDependencies += ws
libraryDependencies += guice

lazy val root = (project in file(".")).
  enablePlugins(PlayScala, BuildInfoPlugin, LauncherJarPlugin, JDebPackaging, RpmPlugin).
  settings(
    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
    buildInfoPackage := "models"
  )

sources in (Compile, doc) := Seq.empty

enablePlugins(JavaServerAppPackaging)
enablePlugins(SystemdPlugin)

pipelineStages := Seq(digest, gzip)

serverLoading := Some(ServerLoader.Systemd)
systemdSuccessExitStatus in Debian += "143"
systemdSuccessExitStatus in Rpm += "143"
linuxPackageMappings += packageTemplateMapping(s"/var/lib/${packageName.value}")() withUser((daemonUser in Linux).value) withGroup((daemonGroup in Linux).value)
