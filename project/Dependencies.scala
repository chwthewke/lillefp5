import sbt._
import sbt.Keys._

object Dependencies {
  def depsGroup(organization: String, version: String)(artifacts: String*)(testArtifacts: String*) =
    artifacts.map(organization       %% _ % version withSources ()) ++
      testArtifacts.map(organization %% _ % version % "test" withSources ())

  val kindProjector = compilerPlugin("org.spire-math" %% "kind-projector" % "0.9.3")

  val catsVersion = "0.9.0"

  val cats = depsGroup("org.typelevel", catsVersion)("cats-core")("cats")

  val mouse = ("com.github.benhutchison" %% "mouse" % "0.6").withSources().exclude("org.typelevel", "cats")

  val catsAll = cats ++ Seq(mouse)

  val scalaArm = "com.jsuereth" %% "scala-arm" % "2.0" withSources ()

  val scalatest = "org.scalatest" %% "scalatest" % "3.0.1" withSources ()

  val scalacheck =
    Seq("org.scalacheck" %% "scalacheck" % "1.13.4", "io.github.amrhassan" %% "scalacheck-cats" % "0.3.2")
      .map(_.withSources())

  val monocleVersion = "1.4.0"

  val monocle =
    depsGroup("com.github.julien-truffaut", monocleVersion)("monocle-core", "monocle-macro")()

  val circeVersion = "0.6.1"

  val circe =
    depsGroup("io.circe", circeVersion)("circe-core", "circe-generic", "circe-optics", "circe-parser")()
      .map(_.exclude("com.github.julien-truffaut", "monocle-core_2.12"))

  val common = cats ++ Seq(kindProjector)
}
