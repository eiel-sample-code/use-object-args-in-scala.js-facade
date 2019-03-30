lazy val scala212 = "2.12.8"
lazy val scala213 = "2.13.0-M5"

ThisBuild / organization := "info.eiel"
ThisBuild / scalaVersion := scala212
ThisBuild / crossScalaVersions := Seq(scala212, scala213)

ThisBuild / scalacOptions ++= ScalacOptions.basic
ThisBuild / scalacOptions ++= (CrossVersion.partialVersion(scalaVersion.value) match {
  case Some((2, 12)) => ScalacOptions.forScala212
  case _ => Seq()
})
ThisBuild / scalacOptions ++= ScalacOptions.forScalaJS

lazy val root = (project in file(".")).
  enablePlugins(ScalaJSPlugin).
  settings(
    name := "Use object args in Scala.js Facade",
    scalaJSUseMainModuleInitializer := true,
    scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.CommonJSModule) }
  )
