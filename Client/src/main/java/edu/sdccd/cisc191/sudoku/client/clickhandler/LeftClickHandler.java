package edu.sdccd.cisc191.sudoku.client.clickhandler;

import edu.sdccd.cisc191.sudoku.client.board.ValidMoves;
import edu.sdccd.cisc191.sudoku.client.board.SudokuBoard;
import edu.sdccd.cisc191.sudoku.client.networking.ClientSender;
import edu.sdccd.cisc191.sudoku.client.screen.CompletionScreen;
import edu.sdccd.cisc191.sudoku.client.screen.Screen;
import edu.sdccd.cisc191.sudoku.client.screen.game.GameTimer;
import edu.sdccd.cisc191.sudoku.common.PlayerID;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;

/**
 * LeftClickHandler. Extends HandleClicks to get the handClick void and to super it.
 */
public class LeftClickHandler extends HandleClicks {
    ValidMoves moves;
    public static int numberForSideButtons = 0;

    private ArrayList<Integer> conflictingCells;

    /**
     * Creates a new constructor for LeftClickHandler. It takes in a SudokuBoard and ValidMoves object and then supers the SudokuBoard.
     *
     * @param board the board
     * @param moves the moves
     */
    public LeftClickHandler(SudokuBoard board, ValidMoves moves) {
        super(board);
        this.moves = moves;
        conflictingCells = new ArrayList<>();
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
                    CompletionScreen screen = new CompletionScreen();
                    congratsScene = new Scene(screen.startScreen(), 400, 400);
                    stage.setScene(congratsScene);

                    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

                    ClientSender connecter = new ClientSender();
                    connecter.sendToServer("PLAYER_FINISHED:" + PlayerID.getPlayerID());
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

    /**
     * Handles the buttons on the side. Not fully working yet but.
     *
     * @param button the button
     * @param row    the row
     * @param col    the col
     */
    public void handleSideClick(Button button, int row, int col)
    {
        setValue(button, row, col, numberForSideButtons - 1);
        numberForSideButtons = 0;
    }

    /**
        Scans the board to check for if the value is there.
     */
    private void scanBoard(int value) {
        for(int i = 0; i < sudokuBoard.getSize(); i++) {
            for(int j = 0; j < sudokuBoard.getSize(); j++)
            {
                if(sudokuBoard.getCellValue(i, j) == value) {
                    conflictingCells.add(sudokuBoard.getCellValue(i, j));
                }
            }
        }
    }
}
