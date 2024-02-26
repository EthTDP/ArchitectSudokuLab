package edu.sdccd.cisc191.sudoku.client.button;

import edu.sdccd.cisc191.sudoku.client.board.ValidMoves;
import edu.sdccd.cisc191.sudoku.client.board.SudokuBoard;
import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LeftClickHandler extends HandleClicks {

    ValidMoves moves;

    public static int numberForSideButtons = 0;

    public LeftClickHandler(SudokuBoard board, ValidMoves moves) {
        super(board);
        this.moves = moves;
    }

    @Override
    public void handleClick(int cellValue, Stage stage, Button button, int row, int col, Background original) {
        if (checkButton(cellValue, stage, button, row, col, original))
            return;

        button.setOnKeyPressed(e -> {
            try {
                int newValue = Integer.parseInt(e.getText());
                setValue(button, row, col, newValue, original);
            } catch (Exception exception)
            {
                showAlert("Make sure you click a number, not a letter or symbol!!", Alert.AlertType.ERROR);
                button.setBackground(original);
            }
        });
    }

    public void handleSideClick(Button button, int row, int col, Background original)
    {
        setValue(button, row, col, numberForSideButtons - 1, original);
        numberForSideButtons = 0;
    }

    private void setValue(Button button, int row, int col, int value, Background original) {
        if (value >= 0 && value < 10) {
            if (!moves.isValidToPlace(row, col, value)) {
                showAlert("Not Possible!", Alert.AlertType.INFORMATION);
                return;
            }

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

    private boolean checkButton(int cellValue, Stage stage, Button button, int row, int col, Background original) {
        super.handleClick(cellValue, stage, button, row, col, original);

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
            clickedButton = button;
            button.setBackground(background);
        } else {
            clickedButton.setBackground(original);
            clickedButton = button;
            button.setBackground(background);
        }

        return false;
    }
}
