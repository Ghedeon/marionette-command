package co.techsylvania.marionette.command.net2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void startTCPClient() throws IOException {
        client_socket = new Socket(InetAddress.getLocalHost(), 6070);
        System.out.println("Connected to server.");
	}

    public void receiveMessages() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(client_socket.getInputStream()));
        boolean tocontinue = true;
        while (tocontinue) {
            String message = reader.readLine();
            if (message == null) {
                continue;
            }
            System.out.println("Recd: " + message);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        reader.close();
    }

    public void close() throws IOException {
        client_socket.close();
        System.out.println("Client socket closed");
    }
}
