package co.techsylvania.marionette.command.net2;

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
        for (int i = 0; i <10; i++) {
            PrintWriter writer = new PrintWriter(tcpSocket.getOutputStream());
            String message = "szia";
            System.out.println("Writing: " + message);
            writer.println(message);
            writer.close();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
	
	public void closeServer() throws IOException{
		tcpSocket.close();
		System.out.println("Server closed"); 
	}
}
