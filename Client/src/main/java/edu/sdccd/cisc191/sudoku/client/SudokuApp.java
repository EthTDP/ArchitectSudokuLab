package edu.sdccd.cisc191.sudoku.client;

import edu.sdccd.cisc191.sudoku.client.board.SudokuBoard;
import edu.sdccd.cisc191.sudoku.client.button.LeftClickHandler;
import edu.sdccd.cisc191.sudoku.client.button.RightClickHandler;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.awt.*;

public class SudokuApp extends Application {

    private static final int SIZE = 9;

    private static final int height = 575;
    private static final int width = 710;
    private SudokuBoard sudokuBoard;

    private TextField textField;

    private static int numberForSideButtons = 0;

    @Override
    public void start(Stage primaryStage) {


        primaryStage.setTitle("Sudoku");

        BorderPane borderPane = new BorderPane();
        Text text = new Text("  S\tU\tD\tO\tK\tU");
        text.setTabSize(15);
        text.setFont(Font.font(55));
        Button startButton = new Button("Start");
        Text information = new Text();

        information.setTextAlignment(TextAlignment.CENTER);
        information.setText("""
                In order to play Sudoku, you must:
                    Type in a number or click the side button with the number you would like to place down.\s
                    Then you click the square you would like to place the number down on!\s
                    that's it.
                """);

        information.setFont(new Font(30));

        startButton.setOnMouseClicked(e -> {
            if(e.getButton() == MouseButton.PRIMARY)
            {
                setGameScene(primaryStage);
            }
        });

        startButton.setPrefSize(250, 100);
        startButton.setFont(Font.font(25));

        borderPane.setTop(text);
        borderPane.setCenter(startButton);
        borderPane.setBottom(information);

        Scene startScene = new Scene(borderPane, 1250, 575);

        primaryStage.setScene(startScene);
        primaryStage.show();
    }

    private void setGameScene(Stage primaryStage) {
            BorderPane root = new BorderPane();
            GridPane gridPane = new GridPane();
            VBox vBox = new VBox();
            textField = new TextField();
            Text sudoku = new Text();
            sudoku.setText("S\tU\tD\tO\tK\tU");
            sudoku.setTextAlignment(TextAlignment.CENTER);
            sudoku.setFont(Font.font("Comic Sans MS", 45));
            sudoku.setTabSize(10);

            root.setTop(sudoku);

            sudokuBoard = new SudokuBoard();
            sudokuBoard.generateBoard();

            //Set the GridPane for the side buttons
            GridPane pane = createButtonPane();

            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    Button button = new Button();
                    button.setPrefSize(48, 48);
                    int value = sudokuBoard.getCellValue(i, j);
                    //If the value is 0, set the text to " ", if not, set it to the value.
                    button.setText(value == 0 ? " " : String.valueOf(value));

                    //Format Buttons
                    formatButtons(i, button, j);

                    int finalI = i;
                    int finalJ = j;

                    button.setOnMouseClicked(event -> {

                        if(event.getButton() == MouseButton.PRIMARY)
                        {
                            LeftClickHandler leftClickHandler = new LeftClickHandler(sudokuBoard, textField, sudokuBoard.getMoves());

                            if(numberForSideButtons == 0)
                            {
                                try {
                                    leftClickHandler.handleLeftClick(button, finalI, finalJ, textField.getText());
                                }  catch (Exception exception) {
                                    leftClickHandler.showAlert("You didn't put a number!", Alert.AlertType.ERROR);
                                    textField.clear();
                                    textField.requestFocus();
                                }
                            } else {
                                leftClickHandler.handleLeftClick(button, finalI, finalJ, String.valueOf(numberForSideButtons));
                                numberForSideButtons = 0;
                            }
                        } else if(event.getButton() == MouseButton.SECONDARY) {
                            RightClickHandler rightClickHandler = new RightClickHandler(sudokuBoard, textField);
                            rightClickHandler.handleRightClick(button, finalI, finalJ);
                        }
                    });

                    System.out.println("Created Button: " + j + ", " + i);
                    gridPane.add(button, j, i);
                }
            }

            root.setBottom(textField);
            pane.setAlignment(Pos.CENTER);

            //Formatting for cool Sudoku look! (For side buttons)
            pane.setTranslateY(125);
            pane.setTranslateX(-5);
            pane.setHgap(10);
            pane.setVgap(10);

            vBox.getChildren().add(pane);

            root.setRight(vBox);

            //GridPane for main game.
            root.setCenter(gridPane);

        Scene scene = new Scene(root, width, height);

            primaryStage.setScene(scene);

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

            primaryStage.setX((screenSize.getWidth() / 2) - ((double) width / 2));
            primaryStage.setY((screenSize.getHeight() / 2) - ((double) height / 2));

            System.out.println("Showing Scene!");
            primaryStage.show();
        }

    private void formatButtons(int i, Button button, int j) {
        if(i == 3 || i == 6)
        {
            button.setTranslateY(5);
        }

        if (i > 2)
        {
            button.setTranslateY(5);
        }

        if(i > 5)
        {
            button.setTranslateY(10);
        }

        if(j == 3 || j == 6)
        {
            button.setTranslateX(5);
        }

        if(j > 2)
        {
            button.setTranslateX(5);
        }

        if(j > 5)
        {
            button.setTranslateX(10);
        }
    }

    private static GridPane createButtonPane() {
        int value = 1;

        GridPane pane = new GridPane();

        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                Button button = new Button();
                button.setPrefSize(48, 48);
                button.setText(String.valueOf(value));
                value++;

                int finalValue = value;

                button.setOnMouseClicked(e -> {
                    if(e.getButton() == MouseButton.PRIMARY)
                    {
                        numberForSideButtons = finalValue;
                    }
                });

                pane.add(button, j, i);
            }
        }
        return pane;
    }

    public static void main(String[] args) {
        System.out.println("Starting Sudoku!");
        launch(args);
    }
}