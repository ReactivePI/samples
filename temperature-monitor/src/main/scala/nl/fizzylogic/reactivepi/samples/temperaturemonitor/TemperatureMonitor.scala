package nl.fizzylogic.reactivepi.samples.temperaturemonitor

import akka.actor.{ActorLogging, Props, ActorRef, Actor}

import scala.concurrent.ExecutionContext

object TemperatureMonitor {
  case object GetCurrentTemperature

  def props(sensor: ActorRef) = Props(new TemperatureMonitor(sensor))
}

class TemperatureMonitor(sensor: ActorRef) extends Actor with ActorLogging {

  import TemperatureMonitor._
  import scala.concurrent.duration._

  implicit val ec:ExecutionContext = context.system.dispatcher

  context.system.scheduler.schedule(0 seconds,
    5 seconds,self, GetCurrentTemperature)

  def receive = {
    case GetCurrentTemperature => loadTemperatureData()
    case TemperatureSensor.SensorData(temperature) =>
      displayTemperatureData(temperature)
  }

  private def loadTemperatureData() = {
    sensor ! TemperatureSensor.GetActualTemperature
  }

  private def displayTemperatureData(temperature:Int) = {
    log.info(s"The current temperature is $temperature")
  }
}
