package co.techsylvania.marionette.command

import nl.gideondk.sentinel.Server

object Commander {

  def main(args: Array[String]) {
    println("Hello, world!")

    Server(9999, SimpleServerHandler, "Commander Server", SimpleMessage.stages)
  }
}
