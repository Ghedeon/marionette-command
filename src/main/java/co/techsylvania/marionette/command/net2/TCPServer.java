package co.techsylvania.marionette.command.net2;

import co.techsylvania.marionette.command.game2048.GameModel;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer extends Thread {

    private Socket tcpSocket;
    private ServerSocket server;

    public void run() {
        TCPServer tcpServer = new TCPServer();
        try {
            tcpServer.waitConnection();

            tcpServer.closeServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void waitConnection() throws IOException {
        server = new ServerSocket(6070);
        tcpSocket = server.accept();
        System.out.println("server accepted");
    }

    public boolean isConnected() {
        return tcpSocket.isConnected();
    }

    public void sendGameModel(GameModel gameModel) throws IOException {
        final DataOutputStream doStream = new DataOutputStream(tcpSocket.getOutputStream());
        System.out.println("Writing :" + gameModel.toString());

        doStream.write();
        write3DMatrix(, gameModel.getMatrix());



    }

    private void closeServer() throws IOException{
        tcpSocket.getOutputStream().close();
        tcpSocket.close();
        System.out.println("Server closed");
    }

    private void write3DMatrix(int[][][] source) {
        final int size = 4;
        byte[] flat = new byte[size * size * size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    flat[i * size * size + j * size + k] = (byte) source[i][j][k];
                }
            }
        }
        return flat;
    }
}
