package edu.sdccd.cisc191.sudoku.button;

import edu.sdccd.cisc191.sudoku.board.SudokuBoard;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class HandleClicks {
    SudokuBoard sudokuBoard;
    TextField textField;

    public HandleClicks(SudokuBoard board, TextField field) {
        sudokuBoard = board;
        textField = field;
    }

    void handleLeftClick(Button button, int row, int col) {
        if (!sudokuBoard.isEditableCell(row, col)) {
            showAlert("You can't change this cell!", Alert.AlertType.ERROR);
            return;
        }

        if (sudokuBoard.isSolved()) {
            showAlert("Congratulations! You solved the puzzle!", Alert.AlertType.CONFIRMATION);
        }
    }

    void handleRightClick(Button button, int row, int col) {
        //Remove later!
        if (!sudokuBoard.isEditableCell(row, col)) {
            showAlert("You can't change this cell!", Alert.AlertType.ERROR);
            return;
        }

        if (sudokuBoard.isSolved()) {
            showAlert("Congratulations! You solved the puzzle!", Alert.AlertType.CONFIRMATION);
        }
    }


    void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Sudoku");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
