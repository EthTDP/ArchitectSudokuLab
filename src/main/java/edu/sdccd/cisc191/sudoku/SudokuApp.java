package edu.sdccd.cisc191.sudoku;

import edu.sdccd.cisc191.sudoku.board.ValidMoves;
import edu.sdccd.cisc191.sudoku.board.SudokuBoard;
import edu.sdccd.cisc191.sudoku.button.LeftClickHandler;
import edu.sdccd.cisc191.sudoku.button.RightClickHandler;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class SudokuApp extends Application {

    private static final int SIZE = 9;

    private static final int height = 575;
    private static final int width = 710;
    private SudokuBoard sudokuBoard;

    private TextField textField;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        GridPane gridPane = new GridPane();
        textField = new TextField();
        Text sudoku = new Text();
        sudoku.setText("S\tU\tD\tO\tK\tU");
        sudoku.setTextAlignment(TextAlignment.CENTER);
        sudoku.setFont(Font.font("Comic Sans MS", 45));
        sudoku.setTabSize(10);

        Text information = new Text();
        information.setTranslateY(height - 125);
        information.setText("Left Click sets the number into the spot.\nRight Click puts a number in and can be changed.");
        information.setTextAlignment(TextAlignment.RIGHT);

        root.setTop(sudoku);

        sudokuBoard = new SudokuBoard();
        sudokuBoard.generateBoard();

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

                button.setOnMouseClicked(e -> {
                    if(e.getButton() == MouseButton.PRIMARY)
                    {
                        LeftClickHandler leftClickHandler = new LeftClickHandler(sudokuBoard, textField, sudokuBoard.getMoves());
                        leftClickHandler.handleLeftClick(button, finalI, finalJ);
                    } else if(e.getButton() == MouseButton.SECONDARY) {
                        RightClickHandler rightClickHandler = new RightClickHandler(sudokuBoard, textField);
                        rightClickHandler.handleRightClick(button, finalI, finalJ);
                    }
                });

                System.out.println("Created Button: " + j + ", " + i);
                gridPane.add(button, j, i);
            }
        }
        //Set the gridpane for the side buttons
        GridPane pane = createButtonPane();

        root.setBottom(textField);
        pane.setAlignment(Pos.CENTER);
        //Formatting for cool Sudoku look! (For side buttons)
        pane.setTranslateY(-25);
        pane.setTranslateX(-10);
        pane.setHgap(10);
        pane.setVgap(10);

        root.setRight(pane);

        //Gridpane for main game.
        root.setCenter(gridPane);

        Scene scene = new Scene(root, width, height);

//        root.setRight(information);

        primaryStage.setTitle("Sudoku");
        primaryStage.setScene(scene);
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