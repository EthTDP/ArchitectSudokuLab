package edu.sdccd.cisc191.sudoku.client.button;

import edu.sdccd.cisc191.sudoku.client.board.SudokuBoard;
import edu.sdccd.cisc191.sudoku.client.screen.Screen;
import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
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
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;

public class HandleClicks {
    SudokuBoard sudokuBoard;
    public static boolean cont;

    public static Scene congratsScene;
    public static boolean finished;

    public static Button clickedButton;

    public static Background original;
    public static boolean pressed = false;

    public HandleClicks(SudokuBoard board) {
        sudokuBoard = board;
    }

    void handleClick(int cellValue, Stage stage, Button button, int row, int col) {

        if (!sudokuBoard.isEditableCell(row, col)) {
            BackgroundFill backgroundFill = new BackgroundFill(Color.DARKORANGE, new CornerRadii(5), Insets.EMPTY);
            Background background = new Background(backgroundFill);

            if(clickedButton == null)
            {
                original = button.getBackground();
                clickedButton = button;
                button.setBackground(background);
            } else {
                clickedButton.setBackground(original);
                original = button.getBackground();
                clickedButton = button;
                button.setBackground(background);
            }

            button.setOnKeyPressed(e -> showAlert("You can't change this cell!", Alert.AlertType.INFORMATION));

            cont = false;
            return;
        }

        cont = true;
    }

    void setValue(Button button, int row, int col, int value) {
        if (value >= 0 && value < 10) {
            ScaleTransition pop = new ScaleTransition();
            RotateTransition rotate = new RotateTransition();
            rotate.setAxis(Rotate.Z_AXIS);
            rotate.setNode(button);
            rotate.setByAngle(360);
            pop.setDuration(Duration.millis(250));
            button.setScaleX(0);
            button.setScaleY(0);
            pop.setToX(1);
            pop.setToY(1);
            pop.setNode(button);
            if(rotate.getStatus() == Animation.Status.STOPPED)
            {
                rotate.play();
                pop.play();
            }

            button.setText(value == 0 ? " " : String.valueOf(value));
            sudokuBoard.setCellValue(row, col, value);
        } else {
            showAlert("More than 1 and less than 10!", Alert.AlertType.INFORMATION);
            button.setBackground(original);
        }
    }

    boolean checkButton(int cellValue, Button button) {
        if(!cont)
        {
            return true;
        }

        CornerRadii radii = new CornerRadii(5);

        BackgroundFill backgroundFill = new BackgroundFill(Color.ORANGE, radii, Insets.EMPTY);
        Background background = new Background(backgroundFill);

        if(cellValue != 0) {
            return true;
        }

        if(clickedButton == null)
        {
            original = button.getBackground();
            clickedButton = button;
            button.setBackground(background);
        } else {
            clickedButton.setBackground(original);
            original = button.getBackground();
            clickedButton = button;
            button.setBackground(background);
        }

        return false;
    }


    public void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Sudoku");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
