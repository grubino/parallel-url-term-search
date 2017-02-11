name := "webcrawl"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "net.databinder.dispatch" %% "dispatch-core" % "0.12.0",
  "com.typesafe.akka" %% "akka-actor" % "2.4.17"
)