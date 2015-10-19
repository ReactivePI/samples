package nl.fizzylogic.reactivepi.samples.temperaturemonitor

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import nl.fizzylogic.reactivepi.I2C
import nl.fizzylogic.reactivepi.i2c.Convert

object TemperatureSensor {

  case object GetActualTemperature

  case class SensorData(temperature: Int)

  def props: Props = Props[TemperatureSensor]
}

class TemperatureSensor extends Actor with ActorLogging {

  import TemperatureSensor._

  // The adafruit MCP9808 sensor is configured to work on address 0x18.
  // You can change this on the device itself by pulling one or more address pins to 3.3V
  val sensor = I2C(1).device(0x18)

  // The ambient temperature register can be read to retrieve the current temperature.
  val MCP9808_REG_AMBIENT_TEMP: Byte = 0x05

  var originalSender: ActorRef = null

  def receive = idle

  def waitingForSensorData: Receive = {
    case I2C.Data(buffer) => sendSensorData(buffer)
  }

  def idle: Receive = {
    case GetActualTemperature => loadTemperatureData()
  }

  private def loadTemperatureData() = {
    log.info("Received request for the temperature")

    context.become(waitingForSensorData)

    originalSender = sender
    sensor ! I2C.Read(MCP9808_REG_AMBIENT_TEMP, 2)
  }

  private def sendSensorData(buffer: Array[Byte]) = {
    originalSender ! SensorData(translateTemperature(buffer))
    context.become(idle)
  }

  private def translateTemperature(buffer: Array[Byte]): Int = {
    val wordData = Convert.wordToInt16(buffer)
    val temperature = Math.round((wordData & 0x0FFF) / 16.0).asInstanceOf[Int]

    if ((wordData & 0x1000) != 0x00) {
      temperature - 256
    } else {
      temperature
    }
  }
}
