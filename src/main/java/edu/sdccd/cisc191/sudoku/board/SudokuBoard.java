package edu.sdccd.cisc191.sudoku.board;

import java.util.Random;

public class SudokuBoard {
    private final int[][] board;
    private final int[][] unsolvedBoard;
    private static final int SIZE = 9;
    private static final int EMPTY_CELL = 0;
    private final ValidMoves moves;

    public SudokuBoard() {
        board = new int[SIZE][SIZE];
        unsolvedBoard = new int[SIZE][SIZE];
        moves = new ValidMoves(board, SIZE, EMPTY_CELL, unsolvedBoard);
    }

    public void generateBoard() {
        // Initialize board with zeros
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = EMPTY_CELL;
            }
        }
        // Generate a complete Sudoku board
        createBoard();
        // Remove some cells to make it a puzzle
        removeCells();

        for(int i = 0; i < SIZE; i++)
        {
            for(int j = 0; j < SIZE; j++)
            {
                unsolvedBoard[i][j] = board[i][j];
            }
        }
    }

    private boolean createBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == EMPTY_CELL) {
                    for (int num = 1; num <= SIZE; num++) {
                        if (moves.isValidMove(row, col, num)) {
                            board[row][col] = num;
                            unsolvedBoard[row][col] = num;
                            if (createBoard()) {
                                return true;
                            }
                            board[row][col] = EMPTY_CELL;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private void removeCells() {
        Random random = new Random();
        int cellsToRemove = SIZE * SIZE / 2;
        while (cellsToRemove > 0) {
            int row = random.nextInt(SIZE);
            int col = random.nextInt(SIZE);
            if (board[row][col] != EMPTY_CELL) {
                board[row][col] = EMPTY_CELL;
                unsolvedBoard[row][col] = EMPTY_CELL;
                cellsToRemove--;
            }
        }
    }

    public int getCellValue(int row, int col) {
        return board[row][col];
    }

    public void setCellValue(int row, int col, int value) {
        board[row][col] = value;
    }

    public boolean isEditableCell(int row, int col) {
        return board[row][col] == EMPTY_CELL;
    }

    public boolean isSolved() {
        // Check if the board is completely filled
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == EMPTY_CELL) {
                    return false;
                }
            }
        }
        // Check if all rows, columns, and boxes contain unique numbers
        for (int i = 0; i < SIZE; i++) {
            if (!moves.isValidRow(i) || !moves.isValidColumn(i) || !moves.isValidBox(i)) {
                return false;
            }
        }
        return true;
    }

    public ValidMoves getMoves()
    {
        return moves;
    }
}
