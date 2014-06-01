package co.techsylvania.marionette.command.net2;

import co.techsylvania.marionette.command.game2048.GameModel;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;

public class TCPClient extends Thread {

    private Socket client_socket;

    public void run() {

        TCPClient tcpClient = new TCPClient();
        try {
            tcpClient.startTCPClient();
            tcpClient.receiveMessages();
            tcpClient.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void startTCPClient() throws IOException {
        client_socket = new Socket(InetAddress.getLocalHost(), 6070);
        System.out.println("Connected to server.");
    }

    public void receiveMessages() throws IOException, ClassNotFoundException {
        final BufferedInputStream oiStream = new BufferedInputStream(client_socket.getInputStream());
        boolean tocontinue = true;
        while (tocontinue) {
            byte[] gameModel = new byte[71];
            int result = oiStream.read(gameModel, 0, 71);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (result == -1) {
                continue;
            }
            System.out.println("Recd: " + gameModel.toString());
        }
        oiStream.close();
    }

    public void close() throws IOException {
        client_socket.close();
        System.out.println("Client socket closed");
    }
}
