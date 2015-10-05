name := """SpacMobileEditor"""

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.6"

routesGenerator := InjectedRoutesGenerator

libraryDependencies ++= Seq(
  "com.google.inject" % "guice" % "3.0",
  "javax.inject" % "javax.inject" % "1",
  "org.reactivemongo" %% "play2-reactivemongo" % "0.11.7.play24",
  "org.mockito" % "mockito-core" % "1.9.5" % "test"
)

lazy val root = (project in file(".")).enablePlugins(PlayScala)