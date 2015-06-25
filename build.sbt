scalaVersion := "2.11.7"

// excludeFilter := "Template.scala"

initialCommands := """
  |import templates._
""".stripMargin

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.4" % Test
)