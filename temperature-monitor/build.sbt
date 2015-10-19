scalaVersion := "2.11.7"

name := "temperature-monitor"

version := "0.1"

resolvers += Resolver.sonatypeRepo("public")
resolvers += Resolver.jcenterRepo

// Akka dependencies
libraryDependencies ++= {
  val akkaVersion = "2.4.0"

  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-remote" % akkaVersion
  )
}


// Reactive PI dependencies
libraryDependencies ++= {
  Seq(
    "reactivepi" %% "core" % "0.1",
    "reactivepi" %% "actors" % "0.1"
  )
}

// Other dependencies
libraryDependencies ++= {
  Seq(
    "com.github.scopt" %% "scopt" % "3.3.0"
  )
}

mainClass in (Compile) := Some("nl.fizzylogic.reactivepi.samples.temperaturemonitor.Program")

assemblyJarName := "temperature-monitor.jar"