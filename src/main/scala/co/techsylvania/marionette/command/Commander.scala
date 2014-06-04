package co.techsylvania.marionette.command

import akka.actor.{ ActorSystem, Props }

object Commander {

  val system = ActorSystem("some-system")
  val mina = system.actorOf(Props[MyEndpoint])

  val sys = ActorSystem("some-system")
  val orders = sys.actorOf(Props[Orders])

  def main(args: Array[String]) {
    println("Hello, world!")

    orders ! <order amount="100" currency="PLN" itemId="12345"/>
  }
}
