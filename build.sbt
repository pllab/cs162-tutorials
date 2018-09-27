
name := "tutorials"

organization := "edu.ucsb.cs.cs162"

version := "1.0.0"

scalaVersion := "2.12.6"

libraryDependencies ++= Seq(
"org.scalatest" %% "scalatest" % "3.0.5" % "test",
"org.scalacheck" %% "scalacheck" % "1.14.0" % "test" withSources() withJavadoc()
)

initialCommands := "import edu.ucsb.cs.cs162.tuts._"

scalacOptions ++= Seq("-deprecation", "-feature")

initialCommands in consoleQuick := ""

fork in Test := true