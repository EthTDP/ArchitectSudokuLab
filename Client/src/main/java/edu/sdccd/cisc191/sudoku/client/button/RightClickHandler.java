package edu.sdccd.cisc191.sudoku.client.button;

import edu.sdccd.cisc191.sudoku.client.board.SudokuBoard;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;

public class RightClickHandler extends HandleClicks {

    public RightClickHandler(SudokuBoard board) {
        super(board);
    }

    @Override
    public void handleClick(int cellValue, Stage stage, Button button, int row, int col) {
        super.handleClick(cellValue, stage, button, row, col);

        if (checkButton(cellValue, button))
            return;

        button.setOnKeyPressed(e -> {
            if (!pressed) {
                try {
                    if (e.getCode() == KeyCode.BACK_SPACE) {
                        setValue(button, row, col, 0);
                    } else {
                        int newValue = Integer.parseInt(e.getText());
                        setValue(button, row, col, newValue);
                    }

                    pressed = true;

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
                    }
                } catch (Exception exception) {
                    showAlert("Make sure you click a number, not a letter or symbol!!", Alert.AlertType.ERROR);
                }
            }
        });

        pressed = false;
    }
}
