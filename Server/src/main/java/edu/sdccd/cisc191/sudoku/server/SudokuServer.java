package edu.sdccd.cisc191.sudoku.server;

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

public class SudokuServer extends Application {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private DataOutputStream out;
    private BufferedReader in;

    private static TextArea text;

    static SudokuServer server;

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

        new Thread(() -> {
            try {
                server.accept();
                System.out.println("Waiting for client input...");

                while (true) {
                    if (server.in == null) {
                        break;
                    }
                    String inputLine = server.in.readLine();
                    if (inputLine == null) {
                        break;
                    }

                    Platform.runLater(() -> {
                        System.out.println(inputLine);
                        text.appendText(inputLine + "\n");
                    });
                }
            } catch (IOException e) {
                System.out.println("Error reading line: " + e.getMessage());
            } finally {
                // Close server resources
                try {
                    server.stop();
                } catch (IOException e) {
                    System.out.println("Error stopping server: " + e.getMessage());
                }
            }
        }).start();
    }

    public void start(int port) throws Exception {
        serverSocket = new ServerSocket(port);
    }

    public void accept() throws IOException {
        clientSocket = serverSocket.accept();
        out = new DataOutputStream(clientSocket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        System.out.println("Client connected.");
    }

    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }

    public static void main(String[] args) {



        try {
            server = new SudokuServer();
            server.start(4444);
            System.out.println("Server started. Waiting for Client...");
            launch(args);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
