name := "webcrawl"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "net.databinder.dispatch" %% "dispatch-core" % "0.12.0",
  "com.typesafe.akka" %% "akka-actor" % "2.4.17",
  "com.typesafe.akka" %% "akka-testkit" % "2.4.17" % "test",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "org.scalamock" %% "scalamock-scalatest-support" % "3.5.0" % "test"
)