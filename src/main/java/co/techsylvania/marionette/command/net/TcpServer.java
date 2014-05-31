package co.techsylvania.marionette.command.net;

import co.techsylvania.marionette.command.commands.Command;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class TcpServer {

    public final static int COMM_PORT = 5050;  // socket port for client comms

    private ServerSocket serverSocket;
    private InetSocketAddress inboundAddr;

    private Socket socket;
    private ObjectOutputStream ooStream;

    /**
     * Default constructor.
     */
    public TcpServer() {
        initServerSocket();
        try {
            while (true) {
                // listen for and accept a client connection to serverSocket
                socket = this.serverSocket.accept();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (SecurityException se) {
            System.err.println("Unable to get host address due to security.");
            System.err.println(se.toString());
            System.exit(1);
        } catch (IOException ioe) {
            System.err.println("Unable to read data from an open socket.");
            System.err.println(ioe.toString());
            System.exit(1);
        } finally {
            try {
                this.serverSocket.close();
            } catch (IOException ioe) {
                System.err.println("Unable to close an open socket.");
                System.err.println(ioe.toString());
                System.exit(1);
            }
        }
    }

    /**
     * Initialize a server socket for communicating with the client.
     */
    private void initServerSocket() {
        this.inboundAddr = new InetSocketAddress(COMM_PORT);
        try {
            this.serverSocket = new java.net.ServerSocket(COMM_PORT);
            assert this.serverSocket.isBound();
            if (this.serverSocket.isBound()) {
                System.out.println("SERVER inbound data port " +
                        this.serverSocket.getLocalPort() +
                        " is ready and waiting for client to connect...");
            }
        } catch (SocketException se) {
            System.err.println("Unable to create socket.");
            System.err.println(se.toString());
            System.exit(1);
        } catch (IOException ioe) {
            System.err.println("Unable to read data from an open socket.");
            System.err.println(ioe.toString());
            System.exit(1);
        }
    }

    public void sendOut(Command command) {
        try {
            ooStream = new ObjectOutputStream(socket.getOutputStream());
            ooStream.writeObject(command);
            ooStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
