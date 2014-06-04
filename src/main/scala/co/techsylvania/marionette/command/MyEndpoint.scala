package co.techsylvania.marionette.command

import akka.camel.{ CamelMessage, Consumer }

class MyEndpoint extends Consumer {
  def endpointUri = "mina2:tcp://localhost:6200?textline=true"

  def receive = {
    case msg: CamelMessage => { /* ... */ }
    case _                 => { /* ... */ }
  }
}

