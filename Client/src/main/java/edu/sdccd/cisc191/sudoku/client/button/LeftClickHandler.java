package edu.sdccd.cisc191.sudoku.client.button;

import edu.sdccd.cisc191.sudoku.client.board.ValidMoves;
import edu.sdccd.cisc191.sudoku.client.board.SudokuBoard;
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
    public void handleLeftClick(Button button, int row, int col, String value) {
        super.handleLeftClick(button, row, col, value);

        if(!cont)
        {
            return;
        }

        int newValue = Integer.parseInt(value);

        if (newValue >= 0 && newValue < 10) {
            if (!moves.isValidToPlace(row, col, newValue)) {
                showAlert("Not possible!", Alert.AlertType.ERROR);
                textField.clear();
                textField.requestFocus();
                return;
            }

            button.setText(newValue == 0 ? " " : String.valueOf(newValue));
            sudokuBoard.setCellValue(row, col, newValue);
            textField.clear();
            textField.requestFocus();
        } else {
            showAlert("More than 1 and less than 10!", Alert.AlertType.INFORMATION);
            textField.clear();
            textField.requestFocus();
        }

    }
}
