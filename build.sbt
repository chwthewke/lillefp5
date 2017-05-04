import sbt._
import sbt.Keys._

// format: off
scalaOrganization in ThisBuild := "org.scala-lang"
scalaVersion      in ThisBuild := "2.12.2"
// format: on

val catserrorsScalacOptions = Seq(
  "-deprecation",
  "-encoding",
  "UTF-8",
  "-feature",
  "-language:higherKinds",
  "-unchecked",
  "-Xfuture",
  "-Xlint",
  "-Yno-adapted-args",
  "-Ypartial-unification",
  "-Ywarn-numeric-widen"
)

val catserrorsConsoleScalacOptions = catserrorsScalacOptions.filterNot(_ == "-Ywarn-unused-import")

val catserrorsTestScalacOptions = catserrorsScalacOptions.filterNot(_ == "-Ywarn-valueDiscard")

val sharedSettings = Seq(organization := "net.chwthewke")

// format: off
val catserrorsScalacSettings = Seq(
  // scalacOptions                       := catserrorsScalacOptions,
  // Note: uncomment this when importing to IntelliJ IDEA
  // scalacOptions                       := catserrorsTestScalacOptions,
  scalacOptions in compile in Compile ++= catserrorsScalacOptions,
  scalacOptions in console in Compile ++= catserrorsConsoleScalacOptions,
  scalacOptions in compile in Test    ++= catserrorsTestScalacOptions,
  scalacOptions in console in Test    ++= catserrorsConsoleScalacOptions
)
// format: on

val catserrorsSettings =
  sharedSettings ++
    Defaults.coreDefaultSettings ++
    catserrorsScalacSettings :+
    (libraryDependencies ++= Dependencies.common) :+
    (testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-oDF"))

val `cats-errors-examples` = project
  .settings(catserrorsSettings)
  .settings(SbtBuildInfo.buildSettings("CatserrorsexamplesBuildInfo"))
  .settings(Console.coreImports.settings)

val `cats-errors` = project
  .in(file("."))
  .settings(sharedSettings)
  .aggregate(`cats-errors-examples`)
