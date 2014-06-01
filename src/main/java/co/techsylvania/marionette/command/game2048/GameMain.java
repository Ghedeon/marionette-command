package co.techsylvania.marionette.command.game2048;

import co.techsylvania.marionette.command.net2.TCPClient;
import co.techsylvania.marionette.command.net2.TCPServer;

public class GameMain {

    public static void main(String args[]) {
        Thread server_thread = new Thread (new TCPServer(),"server_thread");
        server_thread.start();

        Thread cl_thread1 = new Thread (new TCPClient(), "cl1");
        Thread cl_thread2 = new Thread (new TCPClient(), "cl2");
        cl_thread1.start();
        cl_thread2.start();
    }
}
