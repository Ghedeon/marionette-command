package co.techsylvania.marionette.command;

import co.techsylvania.marionette.command.commands.Command;
import co.techsylvania.marionette.command.commands.CommandType;
import co.techsylvania.marionette.command.net.TcpServer;
import scala.Array;

public class CommanderJava {

    public static void main2(String args[]) {
        TcpServer tcpServer = new TcpServer();
        for (int i = 0; i <10; i++) {
            System.out.println("Sending out command...");
            tcpServer.sendOut(new Command(CommandType.MOVE_LEFT, Array.emptyByteArray()));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
