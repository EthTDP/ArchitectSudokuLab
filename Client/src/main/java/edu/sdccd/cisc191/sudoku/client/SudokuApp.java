package edu.sdccd.cisc191.sudoku.client;

import edu.sdccd.cisc191.sudoku.client.files.BoardtoFile;
import edu.sdccd.cisc191.sudoku.client.networking.ClientSender;
import edu.sdccd.cisc191.sudoku.client.networking.ServerConnection;
import edu.sdccd.cisc191.sudoku.client.screen.start.Start;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

import static edu.sdccd.cisc191.sudoku.client.files.BoardtoFile.closeInputStream;
import static edu.sdccd.cisc191.sudoku.client.files.BoardtoFile.closeWriter;

public class SudokuApp extends Application {

    private static final String ROOT_DIRECTORY = "D:\\CISC191\\Labs\\ArchitectSudokuLab\\Client\\src\\main\\java\\resources";
    //Networking Variables

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sudoku");

        Start borderPane = new Start(primaryStage);
        borderPane.startScreen();

        Scene startScene = new Scene(borderPane, 1250, 575);

        primaryStage.setScene(startScene);
        primaryStage.show();
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Starting Sudoku!");

        BoardtoFile file = new BoardtoFile(ROOT_DIRECTORY, "save.txt");
            if(file.delete())
                System.out.println("Deleted!");

        BoardtoFile readSaveFile = new BoardtoFile(ROOT_DIRECTORY, "readable_save.txt");
            if(readSaveFile.delete())
                System.out.println("Deleted!");

        launch(args);

        Platform.runLater(() -> {
            try {
                closeInputStream();
                closeWriter();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}