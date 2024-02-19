package edu.sdccd.cisc191.sudoku.client.button;

import edu.sdccd.cisc191.sudoku.client.board.SudokuBoard;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class RightClickHandler extends HandleClicks {

    public RightClickHandler(SudokuBoard board, TextField field) {
        super(board, field);
    }

    @Override
    public void handleRightClick(Button button, int row, int col) {
        showAlert("it works!", Alert.AlertType.INFORMATION);
    }
}
