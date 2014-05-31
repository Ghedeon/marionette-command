package co.techsylvania.marionette.command.net2;

public class TCPManager {

	public static void main(String[] args) {
		Thread server_thread = new Thread (new TCPServer(),"server_thread");
        Thread client_thread = new Thread (new TCPClient(), "client_thread");

        server_thread.start();
		client_thread.start();
	}
}
