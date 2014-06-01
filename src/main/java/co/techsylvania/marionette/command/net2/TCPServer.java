package co.techsylvania.marionette.command.net2;

import co.techsylvania.marionette.command.game2048.GameModel;
import co.techsylvania.marionette.command.game2048.Matrix;
import co.techsylvania.marionette.command.leap.LeapController;
import co.techsylvania.marionette.command.leap.LeapGestureListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TCPServer extends Thread {

    private List<Thread> senderThreads = new ArrayList<Thread>();
    private List<Sender> senders = new ArrayList<Sender>();
    private ServerSocket server;
    private Matrix matrix = new Matrix();

    public void run() {
        final TCPServer tcpServer = new TCPServer();
        try {
            tcpServer.waitConnection();

            LeapController controller = new LeapController(new LeapGestureListener() {
                @Override
                public void onGestureReceived(GestureType gesture) {
                    switch (gesture) {
                        case LEFT:
                            System.out.println("LEFT");
                            matrix.moveLeft();
//                            matrix.print();
                            break;
                        case RIGHT:
                            System.out.println("RIGHT");
                            matrix.moveRight();
//                            matrix.print();

                            break;
                        case UP:
                            System.out.println("UP");
                            matrix.moveUp();
//                            matrix.print();

                            break;
                        case DOWN:
                            System.out.println("DOWN");
                            matrix.moveDown();
//                            matrix.print();

                            break;
                        case PUSH:
                            System.out.println("PUSH");
                            matrix.moveBackward();
//                            matrix.print();

                            break;
                        case PULL:
                            System.out.println("PULL");
                            matrix.moveForward();
//                            matrix.print();

                            break;
                        default:
                            break;
                    }

                    tcpServer.sendGameModel(mapGameModel());
                }
            });
            tcpServer.sendGameModel(mapGameModel());
            controller.start();
            tcpServer.closeServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendGameModel(GameModel gameModel) {
        for (Sender sender : senders) {
            sender.sendGameModel(gameModel);
        }
    }

    private GameModel mapGameModel() {
        final GameModel gameModel = new GameModel(matrix.getMatrix(),
                matrix.getScore(),
                matrix.getMaxTile(),
                false,
                matrix.isWon(),
                new int[]{0, 0, 0}
        );
        return gameModel;
    }

    private void waitConnection() throws IOException {
        server = new ServerSocket(6070);
        System.out.println("Waiting for connections...");
        final boolean always = true;

        Runnable r = new Runnable() {

            @Override
            public void run() {
                try {
                    while (always) {
                        Socket socket = server.accept();
                        Sender sender = new Sender(socket);
                        final Thread senderThread = new Thread(sender);
                        senderThreads.add(senderThread);
                        senders.add(sender);
                        System.out.println("Server accepted a connection.");
                        senderThread.start();
                        sender.sendGameModel(mapGameModel());
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    private void closeServer() throws IOException {
        for (Thread t : senderThreads) {
            t.interrupt();
        }
        System.out.println("Server closed");
    }
}
