package co.techsylvania.marionette.command.game2048;

import co.techsylvania.marionette.command.net2.TCPServer;

public class GameMain {

    public static void main(String args[]) {
        TCPServer tcpServer = new TCPServer();
        Thread server_thread = new Thread (new TCPServer(),"server_thread");
        server_thread.start();
        while (!tcpServer.isConnected()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Matrix matrix = new Matrix();

        matrix.getMatrix()

        byte[] gameModel =
        tcpServer.sendCommand();

    }
}
