package edu.sdccd.cisc191.sudoku.client.networking;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnection {
    private Socket clientSocket;
    private DataOutputStream out;
    private BufferedReader in;

    public ServerConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new DataOutputStream(clientSocket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public void sendData(String data) throws IOException {
        out.writeBytes(data);
        out.flush();
    }

    public String receiveData() throws  IOException {
        return in.readLine();
    }

    public void close() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
}

