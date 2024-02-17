package edu.sdccd.cisc191.sudoku.button;

import edu.sdccd.cisc191.sudoku.board.ValidMoves;
import edu.sdccd.cisc191.sudoku.board.SudokuBoard;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LeftClickHandler extends HandleClicks {

    ValidMoves moves;

    public LeftClickHandler(SudokuBoard board, TextField field, ValidMoves moves) {
        super(board, field);
        this.moves = moves;
    }

    @Override
    public void handleLeftClick(Button button, int row, int col) {
        super.handleLeftClick(button, row, col);

        try {
            int newValue = Integer.parseInt(textField.getText());
            if (newValue >= 0 && newValue < 10) {
                if (!moves.isValidMove(row, col, newValue)) {
                    showAlert("Not possible!", Alert.AlertType.ERROR);
                    return;
                }

                button.setText(newValue == 0 ? " " : String.valueOf(newValue));
                sudokuBoard.setCellValue(row, col, newValue);
                textField.deleteText(0, textField.getText().length());
            } else {
                showAlert("More than 1 and less than 10!", Alert.AlertType.INFORMATION);
                textField.deleteText(0, textField.getText().length());
            }
        } catch (Exception e) {
            showAlert("You didn't put a number!", Alert.AlertType.ERROR);
            textField.deleteText(0, textField.getText().length());
        }
    }
}
