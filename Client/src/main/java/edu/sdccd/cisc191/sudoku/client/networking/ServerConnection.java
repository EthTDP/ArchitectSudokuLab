package edu.sdccd.cisc191.sudoku.client.networking;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The ServerConnection class. Is the class to create the connection to the server.
 */
public class ServerConnection {
    private Socket clientSocket;
    private DataOutputStream out;
    private BufferedReader in;

    /**
     * Create a new ServerConnection constructor with the IP and the port to connect to the server with
     *
     * @param ip   the ip
     * @param port the port
     * @throws IOException the io exception
     */
    public ServerConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new DataOutputStream(clientSocket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    /**
     * Send data.
     *
     * @param data the data to send
     * @throws IOException the io exception
     */
    public void sendData(String data) throws IOException {
        out.writeBytes(data);
        out.flush();
    }

    /**
     * Receive data
     *
     * @return the data
     * @throws IOException the io exception
     */
    public String receiveData() throws  IOException {
        return in.readLine();
    }

    /**
     * Close everything.
     *
     * @throws IOException the io exception
     */
    public void close() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
}

