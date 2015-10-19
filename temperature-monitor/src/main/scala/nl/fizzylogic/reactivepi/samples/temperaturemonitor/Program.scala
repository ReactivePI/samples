package nl.fizzylogic.reactivepi.samples.temperaturemonitor

import akka.actor.ActorSystem

object Program extends App {
  val parser = new scopt.OptionParser[StartupOptions]("monitor") {
    head("monitor", "0.1")

    // Specify a run mode (client or server)
    opt[String]('m',"mode") action { (x,c) =>
      c.copy(client = x.equalsIgnoreCase("client"), server = x.equalsIgnoreCase("server"))
    } text("Application mode")

    checkConfig(opts => {
      if(!opts.server && !opts.client) {
        failure("Invalid application mode. Allowed values: client or server")
      } else {
        success
      }
    })
  }

  parser.parse(args, StartupOptions()) match {
    case Some(options) =>
      val actorSystem = ActorSystem("temperature-monitor")

      // When the application is run in server mode, it will try to access the
      // temperature sensor on the raspberry PI.
      if(options.server) {
        val sensor = actorSystem.actorOf(TemperatureSensor.props, "temperature-sensor")
        val monitor = actorSystem.actorOf(TemperatureMonitor.props(sensor), "temperature-monitor")
      }
    case None =>
  }
}
