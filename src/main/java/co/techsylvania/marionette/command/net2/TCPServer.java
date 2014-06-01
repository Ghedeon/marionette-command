package co.techsylvania.marionette.command.net2;

import co.techsylvania.marionette.command.game2048.GameModel;
import co.techsylvania.marionette.command.game2048.Matrix;
import co.techsylvania.marionette.command.leap.LeapController;
import co.techsylvania.marionette.command.leap.LeapGestureListener;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer extends Thread {

    private Socket tcpSocket;
    private ServerSocket server;
    private Matrix matrix;

    public void run() {
        final TCPServer tcpServer = new TCPServer();
        try {
            tcpServer.waitConnection();

            matrix = new Matrix();

            LeapController controller = new LeapController(new LeapGestureListener() {
                @Override
                public void onGestureReceived(GestureType gesture) {
                    switch (gesture) {
                        case LEFT:
                            System.out.println("LEFT");
                            matrix.moveLeft();
                            matrix.print();
                            break;
                        case RIGHT:
                            System.out.println("RIGHT");
                            matrix.moveRight();
                            matrix.print();

                            break;
                        case UP:
                            System.out.println("UP");
                            matrix.moveUp();
                            matrix.print();

                            break;
                        case DOWN:
                            System.out.println("DOWN");
                            matrix.moveDown();
                            matrix.print();

                            break;
                        case PUSH:
                            System.out.println("PUSH");
                            matrix.moveBackward();
                            matrix.print();

                            break;
                        case PULL:
                            System.out.println("PULL");
                            matrix.moveForward();
                            matrix.print();

                            break;
                        default:
                            break;
                    }

                    final GameModel gameModel = new GameModel(matrix.getMatrix(),
                            matrix.getScore(),
                            matrix.getMaxTile(),
                            false,
                            matrix.isWon(),
                            new int[]{0, 0, 0}
                    );
                    tcpServer.sendGameModel(gameModel);
                }
            });
            controller.start();

            /*System.out.println("Sending random game states...");
            boolean always = true;
            while (always) {
                final GameModel gameModel = new GameModel(matrix.getMatrix(),
                        matrix.getScore(),
                        matrix.getMaxTile(),
                        false,
                        matrix.isWon(),
                        new int[]{0, 0, 0}
                );
                tcpServer.sendGameModel(gameModel);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }*/

            tcpServer.closeServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void waitConnection() throws IOException {
        System.out.println("Waiting for connection...");
        server = new ServerSocket(6070);
        tcpSocket = server.accept();
        System.out.println("Server accepted connection.");
    }

    public boolean isConnected() {
        return tcpSocket != null && tcpSocket.isConnected();
    }

    public void sendGameModel(GameModel gameModel) {
        try {
            sendGameModelThrows(gameModel);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void sendGameModelThrows(GameModel gameModel) throws IOException {
        final DataOutputStream doStream = new DataOutputStream(new BufferedOutputStream(tcpSocket.getOutputStream()));
        System.out.println("Writing :" + gameModel.getScore());
        write3DMatrix(doStream, gameModel.getMatrix());
        doStream.writeInt(gameModel.getScore());
        doStream.writeInt(gameModel.getMaxTile());
        doStream.writeInt(booleanToInt(gameModel.isGameOver()));
        doStream.writeInt(booleanToInt(gameModel.isWon()));
        writeAngles(doStream, gameModel.getAngles());
        doStream.flush();
    }

    private void closeServer() throws IOException {
        tcpSocket.getOutputStream().close();
        tcpSocket.close();
        System.out.println("Server closed");
    }

    private void write3DMatrix(DataOutputStream doStream, int[][][] source) throws IOException {
        final int size = 4;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    doStream.writeInt(source[i][j][k]);
                }
            }
        }
    }

    private void writeAngles(DataOutputStream doStream, int[] angles) throws IOException {
        for (int i = 0; i < 3; i++) {
            doStream.writeInt(angles[i]);
        }
    }

    private int booleanToInt(boolean b) {
        if (b) {
            return 1;
        }
        return 0;
    }
}
