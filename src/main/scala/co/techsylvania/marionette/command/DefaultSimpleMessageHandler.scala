package co.techsylvania.marionette.command

import SimpleMessage._
import nl.gideondk.sentinel.{ConsumerAction, Resolver}

trait DefaultSimpleMessageHandler extends Resolver[SimpleMessageFormat, SimpleMessageFormat] {
  def process = {
    case SimpleStreamChunk(x) ⇒ if (x.length > 0) ConsumerAction.ConsumeStreamChunk else ConsumerAction.EndStream

    case x: SimpleError       ⇒ ConsumerAction.AcceptError
    case x: SimpleReply       ⇒ ConsumerAction.AcceptSignal
  }
}

object SimpleClientHandler extends DefaultSimpleMessageHandler
