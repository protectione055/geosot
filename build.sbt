ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.11"

lazy val root = (project in file("."))
  .settings(
    name := "geosot",
    scalacOptions += "-Yrangepos"
  )

libraryDependencies += "com.lihaoyi" %% "os-lib" % "0.9.1"
libraryDependencies += "com.lihaoyi" %% "geny" % "1.0.0"
libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test
