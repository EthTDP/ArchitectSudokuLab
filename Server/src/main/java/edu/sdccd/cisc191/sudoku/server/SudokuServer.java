package edu.sdccd.cisc191.sudoku.server;

import edu.sdccd.cisc191.sudoku.common.PlayerID;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.TextArea;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static edu.sdccd.cisc191.sudoku.common.winner.CheckWinner.*;

/**
 * The class SudokuServer which extends Application for JavaFX. It starts the server and does networking.
 */
public class SudokuServer extends Application {

    private ServerSocket serverSocket;

    private static TextArea text;
    static SudokuServer server;
    static BufferedReader in;
    static DataOutputStream out;
    public static Socket clientSocket;

    private static final int MAX_CLIENTS = 2;
    public static int connectedClients = 0;
    static int playerID;

    @Override
    public void start(Stage stage) {
        BorderPane pane = new BorderPane();
        Text serverText = new Text("SERVER!");
        serverText.setFont(Font.font("impact", 45));
        serverText.setTextAlignment(TextAlignment.CENTER);
        serverText.setTranslateX(125);
        pane.setBottom(serverText);
        text = new TextArea();
        text.setEditable(false);
        pane.setCenter(text);

        Scene scene = new Scene(pane, 400, 400);
        stage.setScene(scene);
        stage.setTitle("Server!");
        stage.show();

        new Thread(this::startServer).start();
    }

    public void startServer() {
        try {
            server = new SudokuServer();
            server.start(4444);
            Platform.runLater(() -> {
                text.appendText("Server started. Waiting for Client...\n");
            });
            while(true) {
                if(connectedClients < MAX_CLIENTS) {
                    try {
                        clientSocket = server.serverSocket.accept();
                        playerID = PlayerID.getPlayerID();
                        out = new DataOutputStream(clientSocket.getOutputStream());
                        out.writeBytes("SERVER_NOT_FULL\n");
                        out.flush();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    connectedClients++;

                    System.out.println("Client " + connectedClients + " has connected!");
                    Thread clientThread = new Thread(new ClientHandler(clientSocket, connectedClients));
                    clientThread.start();
                } else {
                    try {
                        Socket rejectSocket = server.serverSocket.accept();
                        DataOutputStream rejectOut = new DataOutputStream(rejectSocket.getOutputStream());
                        rejectOut.writeBytes("SERVER_FULL\n");
                        rejectOut.flush();
                        rejectSocket.close();
                    } catch (IOException e) {
                        System.out.println("Error handling rejected client: " + e.getMessage());
                    }
                }
            }
        } catch(Exception e) {
            System.out.println("Error starting server: " + e.getMessage() + "\n");
        }
    }

    /**
     * Start the server.
     *
     * @param port the port
     * @throws Exception the exception
     */
    public void start(int port) throws Exception {
        serverSocket = new ServerSocket(port);
    }

    public ServerSocket currentServerSocket() {
         return serverSocket;
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private int connectedClients;

        /**
         * Create a new ClientHandler arged constructor with the clientSocked and the number of connectedClients.
         *
         * @param clientSocket     the client socket
         * @param connectedClients the connected clients
         */
        public ClientHandler(Socket clientSocket, int connectedClients)
        {
            this.clientSocket = clientSocket;
            this.connectedClients = connectedClients;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                Platform.runLater(() -> text.appendText("Client connected: " + connectedClients + "\n"));

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    if(!getWinnerExists()) {
                        if(inputLine.startsWith("PLAYER_FINISHED:")) {
                            setWinnerExists(true);
                            String playerID = inputLine.substring("PLAYER_FINISHED:".length());

                            String broadcastMessage = "PLAYER_FINISHED:" + playerID;
                            broadcastToClients(broadcastMessage);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Error handling client: " + e.getMessage());
            } finally {
                try {
                    clientSocket.close();
                    Platform.runLater(() -> text.appendText("Client " + connectedClients + " disconnected\n"));
                    connectedClients--; // Decrement the counter when the client disconnects
                    System.out.println("Client disconnected.");
                } catch (IOException e) {
                    System.out.println("Error closing client socket: " + e.getMessage());
                }
            }
        }

        private void broadcastToClients(String message) {
            for (int i = 0; i < 2; i++) {
                try {
                    DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
                    out.writeBytes(message + "\n");
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}



