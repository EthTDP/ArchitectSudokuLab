package edu.sdccd.cisc191.sudoku.client.networking;


import edu.sdccd.cisc191.sudoku.common.PlayerID;

import java.io.IOException;

/**
 * The class ClientSender. Sends things from the client and also receives things from the server.
 */
public class ClientSender {
    private static ServerConnection connect;

    /**
     * Instantiates a new Client sender.
     *
     * @param ip       the ip
     * @param port     the port
     * @param playerID the player id
     * @throws IOException the io exception
     */
    public ClientSender(String ip, int port, int playerID) throws IOException {
        connect = new ServerConnection(ip, port);
        sendPlayerID(playerID);
    }

    /**
     * Create a no-args constructor that won't do anything but create an object so we can send and receive.
     */
    public ClientSender() {
    }

    /**
     * Receive from server.
     *
     * @return the string
     * @throws IOException the io exception
     */
    public String receiveFromServer() throws IOException {
        return connect.receiveData();
    }

    /**
     * Send to the server.
     *
     * @param data the data
     * @throws IOException the io exception
     */
    public void sendToServer(String data) throws IOException {
        connect.sendData(data);
    }

    /**
     * Close the connection.
     *
     * @throws IOException the io exception
     */
    public void closeConnection() throws IOException {
        connect.close();
    }

    private void sendPlayerID(int playerID) {
        PlayerID.setPlayerID(playerID);
    }
}
