package edu.sdccd.cisc191.sudoku.client.networking;


import java.io.IOException;

public class ClientSender {
    private static ServerConnection connect;

    public ClientSender(String ip, int port) throws IOException {
        connect = new ServerConnection(ip, port);
    }

    public ClientSender() {
    }

    public void sendToServer(String data) throws IOException {
        connect.sendData(data);
    }

    public void closeConnection() throws IOException {
        connect.close();
    }
}
