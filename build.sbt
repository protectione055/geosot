ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.11"

lazy val root = (project in file("."))
  .settings(
    name := "geosot"
  )

libraryDependencies += Seq(
  "com.lihaoyi" %% "os-lib" % "0.9.1",
  "com.lihaoyi" %% "geny" % "1.0.0"
)
