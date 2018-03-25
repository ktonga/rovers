name := "rovers"

version := "1.0"

scalaVersion := "2.12.4"

val scalaTestV = "3.0.5"

val scalaCheckV = "1.13.5"

val scalazV = "7.2.20"

libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-core" % scalazV,
  "org.scalatest" %% "scalatest" % scalaTestV % "test",
  "org.scalacheck" %% "scalacheck" % scalaCheckV % "test"
)

scalacOptions ++= Seq(
  "-target:jvm-1.8",
  "-Xfatal-warnings",
  "-Ywarn-unused",
  "-Ywarn-unused-import",
  "-deprecation"
)
