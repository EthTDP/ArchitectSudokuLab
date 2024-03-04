package edu.sdccd.cisc191.sudoku.client.button;

import edu.sdccd.cisc191.sudoku.client.SudokuApp;
import edu.sdccd.cisc191.sudoku.client.board.ValidMoves;
import edu.sdccd.cisc191.sudoku.client.board.SudokuBoard;
import edu.sdccd.cisc191.sudoku.client.networking.ClientSender;
import edu.sdccd.cisc191.sudoku.client.screen.game.GameTimer;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;

public class LeftClickHandler extends HandleClicks {

    ValidMoves moves;

    public static int numberForSideButtons = 0;

    public LeftClickHandler(SudokuBoard board, ValidMoves moves) {
        super(board);
        this.moves = moves;
    }

    @Override
    public void handleClick(int cellValue, Stage stage, Button button, int row, int col) {
        super.handleClick(cellValue, stage, button, row, col);

        if (checkButton(cellValue, button))
            return;

        button.setOnKeyPressed(e -> {
            pressed = false;
            try {
                if(e.getCode() == KeyCode.BACK_SPACE) {
                    setValue(button, row, col, 0);
                } else {
                    int newValue = Integer.parseInt(e.getText());

                    if (!moves.isValidToPlace(row, col, newValue)) {
                        showAlert("Not Possible!", Alert.AlertType.INFORMATION);
                        return;
                    }

                    setValue(button, row, col, newValue);
                }

                if (sudokuBoard.isSolved()) {
                    GameTimer.stopTimer();
                    cont = false;
                    finished = true;
                    BorderPane pane = new BorderPane();
                    Text text = new Text("CONGRATULATIONS! YOU SOLVED THE PUZZLE!!!");
                    Text timeText = new Text("Time: " + GameTimer.seconds--);
                    pane.setCenter(text);
                    pane.setBottom(timeText);
                    congratsScene = new Scene(pane, 400, 400);
                    stage.setScene(congratsScene);

                    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

                    ClientSender connecter = new ClientSender();
                    connecter.sendToServer("What's up server! Here is the minutes and seconds: " +
                            (GameTimer.seconds > 9 ? GameTimer.minutes + ":" + GameTimer.seconds-- : GameTimer.minutes + ":0" + GameTimer.seconds--));
                    connecter.closeConnection();

                    stage.setX((screenSize.getWidth() / 2) - (200));
                    stage.setY((screenSize.getHeight() / 2) - (200));
                }
            } catch (Exception exception)
            {
                showAlert("Make sure you click a number, not a letter or symbol!!", Alert.AlertType.ERROR);
            }
        });
    }

    public void handleSideClick(Button button, int row, int col)
    {
        setValue(button, row, col, numberForSideButtons - 1);
        numberForSideButtons = 0;
    }
}
