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

            tcpServer.sendCommands();

            tcpServer.closeServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void waitConnection() throws IOException {
        server = new ServerSocket(6070);
        tcpSocket = server.accept();
        System.out.println("server accepted");
    }

    public void sendCommands() throws IOException {
        final ObjectOutputStream ooStream = new ObjectOutputStream(tcpSocket.getOutputStream());
        for (int i = 0; i <10; i++) {
            System.out.println("Writing CommandType.MOVE_LEFT");
            ooStream.writeObject(new GameModel(new int[4][4][4], 4, false, new int[] {0, 0, 0}));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeServer() throws IOException{
        tcpSocket.getOutputStream().close();
        tcpSocket.close();
        System.out.println("Server closed");
    }
}
