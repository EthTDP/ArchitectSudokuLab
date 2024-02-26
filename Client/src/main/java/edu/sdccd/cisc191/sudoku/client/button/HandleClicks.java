package edu.sdccd.cisc191.sudoku.client.button;

import edu.sdccd.cisc191.sudoku.client.board.SudokuBoard;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;

public class HandleClicks {
    SudokuBoard sudokuBoard;
    public static boolean cont;

    public static Scene congratsScene;
    public static boolean finished;

    public static Button clickedButton;

    public HandleClicks(SudokuBoard board) {
        sudokuBoard = board;
    }

    void handleClick(int cellValue, Stage stage, Button button, int row, int col, Background original) {

        if (sudokuBoard.isSolved()) {
            cont = false;
            finished = true;
            BorderPane pane = new BorderPane();
            Text text = new Text("CONGRATULATIONS! YOU SOLVED THE PUZZLE!!!");
            pane.setCenter(text);
            congratsScene = new Scene(pane, 400, 400);
            stage.setScene(congratsScene);

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

            stage.setX((screenSize.getWidth() / 2) - (200));
            stage.setY((screenSize.getHeight() / 2) - (200));
            return;
        }

        if (!sudokuBoard.isEditableCell(row, col)) {
            BackgroundFill backgroundFill = new BackgroundFill(Color.DARKORANGE, new CornerRadii(5), Insets.EMPTY);
            Background background = new Background(backgroundFill);
            button.setBackground(background);

            if(clickedButton == null)
            {
                clickedButton = button;
                clickedButton.setBackground(original);
            } else {
                clickedButton.setBackground(original);
                clickedButton = button;
                button.setBackground(background);
            }

            button.setOnKeyPressed(e -> showAlert("You can't change this cell!", Alert.AlertType.INFORMATION));

            cont = false;
            return;
        }

        cont = true;
    }


    public void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Sudoku");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
