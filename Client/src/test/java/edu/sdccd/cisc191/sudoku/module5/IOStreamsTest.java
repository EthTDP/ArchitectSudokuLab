package edu.sdccd.cisc191.sudoku.module5;

import edu.sdccd.cisc191.sudoku.client.SudokuApp;
import edu.sdccd.cisc191.sudoku.client.board.SudokuBoard;
import edu.sdccd.cisc191.sudoku.client.files.BoardtoFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class IOStreamsTest {

    @Test
    void checkSaves() throws IOException {
        SudokuBoard board = new SudokuBoard();
        board.generateBoard();
        BoardtoFile file = new BoardtoFile("D:\\CISC191\\Labs\\ArchitectSudokuLab\\Client\\src\\main\\java\\resources", "save.txt");
        file.writeBoardToFile(board.getUnsolvedBoard(), board.getBoard());
        int[][] values = file.getBoardFromFile();
        Assertions.assertArrayEquals(board.getUnsolvedBoard(), values);
    }
}
