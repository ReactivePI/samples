Temperature-monitor sample
--------------------------
this sample demonstrates the combination of using Akka remote actors with ReactivePI to build a remote temperature
sensor. Please follow the rest of this README to get the sample up and running.

## Building the sample
To build the sample be sure that you have Scala 2.11.7 and SBT 0.13.6 or higher on your system.
Next run the following commands to build the sample:

```
sbt clean package
```

## Running the sample
The sample requires to computers one of which is a Raspberry PI.
First copy the jar file created in the previous section to your Rasperry PI and 
add a file `application.conf` to the folder where you placed the JAR file.
The contents of the `application.conf` file should look like this:

```
akka {
  actor {
    provider = "akka.remote.RemoteActorRefProvider"
  }
  remote {
    enabled-transports = ["akka.remote.netty.tcp"]
    netty.tcp {
      hostname = "127.0.0.1"
      port = 2552
    }
 }
}
```

Start the application with the following command:

```
java -cp .:temperature-monitor_2.11-0.1.jar nl.fizzylogic.reactivepi.samples.temperaturemonitor.Program -m sensor
```

Next copy the jar file to a separate location on your computer and use the following `application.conf`
to set up the application:

```
akka {
  actor {
    provider = "akka.remote.RemoteActorRefProvider"
    deployment {
      /temperature-sensor {
        remote = "akka.tcp://temperature-monitor@<remote-ip>:2552"
      }
    }
  }
  remote {
    enabled-transports = ["akka.remote.netty.tcp"]
    netty.tcp {
      hostname = "127.0.0.1"
      port = 2552
    }
 }
}
```

Be sure to enter the IP-address of the raspberry PI in your config before starting the application using the command below:

```
java -cp .:temperature-monitor_2.11-0.1.jar nl.fizzylogic.reactivepi.samples.temperaturemonitor.Program -m monitor
```