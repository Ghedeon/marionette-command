package co.techsylvania.marionette.command.game2048;

import co.techsylvania.marionette.command.net2.TCPClient;
import co.techsylvania.marionette.command.net2.TCPServer;

public class GameMain {

    public static void main(String args[]) {
        TCPServer tcpServer = new TCPServer();
        Thread server_thread = new Thread (new TCPServer(),"server_thread");
        Thread client_thread = new Thread (new TCPClient(), "client_thread");
        server_thread.start();
        client_thread.start();
        while (!tcpServer.isConnected()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Sending random game states...");
        boolean always = true;
        while (always) {
            final Matrix matrix = new Matrix();
            final GameModel gameModel = new GameModel(matrix.getMatrix(),
                    matrix.getScore(),
                    matrix.getMaxTile(),
                    false,
                    matrix.isWon(),
                    new int[] {0, 0, 0}
            );
            tcpServer.sendGameModel(gameModel);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
