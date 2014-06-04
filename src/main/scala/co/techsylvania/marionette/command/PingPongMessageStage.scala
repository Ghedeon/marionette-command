package co.techsylvania.marionette.command

import akka.io.{PipelineContext, SymmetricPipePair, SymmetricPipelineStage}
import akka.util.ByteString

case class PingPongMessageFormat(s: String)

class PingPongMessageStage extends SymmetricPipelineStage[PipelineContext, PingPongMessageFormat, ByteString] {

  override def apply(ctx: PipelineContext) = new SymmetricPipePair[PingPongMessageFormat, ByteString] {

    override val commandPipeline = { msg: PingPongMessageFormat ⇒
      Seq(Right(ByteString(msg.s)))
    }

    override val eventPipeline = { bs: ByteString ⇒
      Seq(Left(PingPongMessageFormat(new String(bs.toArray))))
    }
  }
}
