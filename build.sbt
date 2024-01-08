organization := "me"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.10"

libraryDependencies += filters

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
libraryDependencies += "ch.megard" %% "akka-http-cors" % "1.2.0"
libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.3.3",
  "org.postgresql" % "postgresql" % "42.3.1" // PostgreSQL JDBC driver
)
libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.3.3",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.3", // Optional but recommended for connection pooling
  "org.postgresql" % "postgresql" % "42.3.1" // PostgreSQL JDBC driver
)

libraryDependencies += "com.google.inject" % "guice" % "4.2.3" // Guice
libraryDependencies += "net.codingwell" %% "scala-guice" % "4.2.6" // Scala Guice
libraryDependencies += "com.google.inject.extensions" % "guice-assistedinject" % "4.2.3"
libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-slick" % "5.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0",
  "org.postgresql" % "postgresql" % "42.3.1" // PostgreSQL JDBC driver
)
libraryDependencies ++= Seq(
  "mysql" % "mysql-connector-java" % "5.1.18"
)
libraryDependencies += "com.stripe" % "stripe-java" % "20.56.0"
herokuAppName in Compile := "frightening-village-02952"


// Adds additional packages into Twirl
//TwirlKeys.templateImports += "me.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "me.binders._"


