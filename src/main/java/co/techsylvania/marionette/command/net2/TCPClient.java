package co.techsylvania.marionette.command.net2;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;


public class TCPClient extends Thread {

	public void run() {
		
		TCPClient tcpClient = new TCPClient();
		try {
			tcpClient.startTCPClient();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void startTCPClient() throws IOException{
		
		Scanner input = new Scanner(System.in);
		String message;
		boolean complete = false;
	
		Socket client_socket = new Socket(InetAddress.getLocalHost(), 6070);
        System.out.println("Connected to server.");
		PrintWriter out = new PrintWriter(client_socket.getOutputStream(),true);



		while (!complete) {
			
			message = "message";
			System.out.println("to be sent: " + message);
			
			out.println(message);
			System.out.println("Message sent");
			
			if (message.compareTo("exit") == 0) {
				complete = true;
			}
			
		}
		out.close();
		client_socket.close();
		System.out.println("Client socket closed");

		
	}

}
