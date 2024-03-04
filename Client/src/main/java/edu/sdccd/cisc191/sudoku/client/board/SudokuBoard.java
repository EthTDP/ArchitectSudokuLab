package edu.sdccd.cisc191.sudoku.client.board;

import edu.sdccd.cisc191.sudoku.client.screen.start.Start;

import java.util.*;

public class SudokuBoard {
    private final int[][] board;
    private final int[][] unsolvedBoard;
    private static final int SIZE = 9;
    private static final int EMPTY_CELL = 0;
    private final ValidMoves moves;

    public SudokuBoard() {
        //Initialize class variables
        board = new int[SIZE][SIZE];
        unsolvedBoard = new int[SIZE][SIZE];
        moves = new ValidMoves(board, SIZE, unsolvedBoard);
    }

    public void generateBoard() {
        // Generate a complete Sudoku board
        createBoard();

        //Copy sudoku board to another array, unsolved array, and then remove the cells for the unsolved array to have a solved and an unsolved array
        for (int i = 0; i < SIZE; i++) {
            System.arraycopy(board[i], 0, unsolvedBoard[i], 0, SIZE);
        }

        // Remove some cells for Sudoku!
        removeCells();

        //Prints the correct board
        System.out.print("Correct Board: \n");
        for (int[] ints : board) {
            for (int j = 0; j < ints.length; j++) {
                System.out.print(ints[j]);
                System.out.print(" ");
            }

            System.out.print("\n");
        }
    }


    private boolean createBoard() {
        ArrayList<Integer> firstRowNumbers = new ArrayList<>();

        for(int i = 1; i <= SIZE; i++)
        {
            firstRowNumbers.add(i);
        }

        Collections.shuffle(firstRowNumbers);

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == EMPTY_CELL) {
                    for (int num : firstRowNumbers) {
                        if (moves.isValidMove(row, col, num)) {
                            board[row][col] = num;
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
        int cellsToRemove;
        switch (Start.difficulty) {
            case "easy" : {
                cellsToRemove = SIZE * SIZE / 3;
                System.out.println(cellsToRemove);
                break;
            }

            case "hard" : {
                cellsToRemove = (int) (SIZE * SIZE / 1.5);
                System.out.println(cellsToRemove);
                break;
            }

            default: {
                cellsToRemove = SIZE * SIZE / 2;
                System.out.println(cellsToRemove);
            }
        }

        while (cellsToRemove > 0) {
            int row = random.nextInt(SIZE);
            int col = random.nextInt(SIZE);
            if (unsolvedBoard[row][col] != EMPTY_CELL) {
                unsolvedBoard[row][col] = EMPTY_CELL;
                cellsToRemove--;
            }
        }
    }


    public int getCellValue(int row, int col) {
        return unsolvedBoard[row][col];
    }


    public void setCellValue(int row, int col, int value) {
        unsolvedBoard[row][col] = value;
    }


    public boolean isEditableCell(int row, int col) {
        return unsolvedBoard[row][col] != board[row][col];
    }


    public boolean isSolved() {
        // Check if the board is completely filled
        for(int i = 0; i < SIZE; i++)
        {
            for(int j = 0; j < SIZE; j++)
            {
                if(unsolvedBoard[i][j] != board[i][j])
                {
                    return false;
                }
            }
        }
        return true;
    }

    public ValidMoves getMoves()
    {
        return moves;
    }
}
