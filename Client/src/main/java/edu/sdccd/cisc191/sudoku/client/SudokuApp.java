package edu.sdccd.cisc191.sudoku.client;

import edu.sdccd.cisc191.sudoku.client.screen.start.Start;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SudokuApp extends Application {

    //Networking Variables
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sudoku");

        Start borderPane = new Start(primaryStage);
        borderPane.startScreen();

        Scene startScene = new Scene(borderPane, 1250, 575);

        primaryStage.setScene(startScene);
        primaryStage.show();
    }


    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public void sendRequest() throws Exception{
        out.println("What's up server!");
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }


    public static void main(String[] args) {
        System.out.println("Starting Sudoku!");

        SudokuApp client = new SudokuApp();
        try {
            client.startConnection("127.0.0.1", 4444);
            System.out.println("Starting Server Connection!");
            client.sendRequest();
            launch(args);
            client.stopConnection();
        } catch (Exception e)
        {
            launch(args);
        }
    }
}