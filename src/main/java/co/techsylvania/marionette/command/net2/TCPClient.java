package co.techsylvania.marionette.command.net2;

import co.techsylvania.marionette.command.commands.Command;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;


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
        final ObjectInputStream oiStream = new ObjectInputStream(client_socket.getInputStream());
        boolean tocontinue = true;
        while (tocontinue) {
            Command command = (Command) oiStream.readObject();
            if (command == null) {
                continue;
            }
            System.out.println("Recd: " + command.getCommandType().name());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        oiStream.close();
    }

    public void close() throws IOException {
        client_socket.close();
        System.out.println("Client socket closed");
    }
}
