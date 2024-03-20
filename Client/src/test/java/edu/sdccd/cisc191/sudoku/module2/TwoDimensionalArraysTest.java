package edu.sdccd.cisc191.sudoku.module2;

import edu.sdccd.cisc191.sudoku.client.board.SudokuBoard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TwoDimensionalArraysTest {

    @Test
    void testBoard() {
        SudokuBoard board = new SudokuBoard();
        for(int i = 0; i < 9; i++)
        {
            for(int j = 0; j < 9; j++) {
                int[][] checkBoard = new int[9][9];
                int value = board.getCellValue(i, j);
                checkBoard[i][j] = value;
                Assertions.assertArrayEquals(board.getUnsolvedBoard(), checkBoard);
            }
        }
    }
}
