package co.techsylvania.marionette.command.game2048;

import co.techsylvania.marionette.command.net2.TCPClient;
import co.techsylvania.marionette.command.net2.TCPServer;

public class GameMain {

    public static void main(String args[]) {
        Thread server_thread = new Thread (new TCPServer(),"server_thread");
        Thread client_thread = new Thread (new TCPClient(), "client_thread");
        server_thread.start();
        client_thread.start();

    }
}
