package edu.sdccd.cisc191.sudoku.client.clickhandler;

import edu.sdccd.cisc191.sudoku.client.board.SudokuBoard;
import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Handler for clicks:
 * Has methods that can be overriden for handling clicks
 */
public class HandleClicks {
    SudokuBoard sudokuBoard;
    public static boolean cont;
    public static Scene congratsScene;
    public static boolean finished;

    public static Button clickedButton;
    public static Background original;
    public static boolean pressed = false;

    /**
     * Create a new constructor that takes a SudokuBoard class and sets it
     *
     * @param board the board
     */
    public HandleClicks(SudokuBoard board) {
        sudokuBoard = board;
    }

    /**
     * Handle click.
     *
     * @param cellValue the cell value
     * @param stage     the stage
     * @param button    the button
     * @param row       the row
     * @param col       the col
     */
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

    /**
     * Sets value.
     *
     * @param button the button
     * @param row    the row
     * @param col    the col
     * @param value  the value
     */
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

    /**
     * Check button boolean. Checks for whether we can continue or not
     *
     * @param cellValue the cell value
     * @param button    the button
     * @return can we continue?
     */
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


    /**
     * Show alert.
     *
     * @param message the message
     * @param type    the type of alert
     */
    public static void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Sudoku");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
