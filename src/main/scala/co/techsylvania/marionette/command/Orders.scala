package co.techsylvania.marionette.command

import akka.actor.Actor
import akka.camel.{Oneway, Producer}

class Orders extends Actor with Producer with Oneway {
  def endpointUri = "jms:queue:Orders"
}
