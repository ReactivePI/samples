scalaVersion := "2.12.6"

name := "temperature-monitor"

version := "0.2"

resolvers += Resolver.sonatypeRepo("public")
resolvers += Resolver.jcenterRepo

// Akka dependencies
libraryDependencies ++= {
  val akkaVersion = "2.5.13"

  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-remote" % akkaVersion
  )
}


// Reactive PI dependencies
libraryDependencies ++= {
  Seq(
    "nl.fizzylogic.reactivepi" %% "core" % "0.3.1",
    "nl.fizzylogic.reactivepi" %% "actors" % "0.3.1"
  )
}

// Other dependencies
libraryDependencies ++= {
  Seq(
    "com.github.scopt" %% "scopt" % "3.7.0"
  )
}

mainClass in (Compile) := Some("nl.fizzylogic.reactivepi.samples.temperaturemonitor.Program")

assemblyJarName := "temperature-monitor.jar"