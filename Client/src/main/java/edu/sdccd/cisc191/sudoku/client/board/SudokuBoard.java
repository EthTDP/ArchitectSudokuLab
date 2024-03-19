package edu.sdccd.cisc191.sudoku.client.board;

import edu.sdccd.cisc191.sudoku.client.screen.start.Start;

import java.util.*;

/**
 * SudokuBoard class:
 * Handles all board stuff like generating it or setting a spot, checking the number in the spot, checking for if it is solve or editable
 */
public class SudokuBoard {
    private final int[][] board;
    private final int[][] unsolvedBoard;
    private static final int SIZE = 9;
    private static final int EMPTY_CELL = 0;
    private final ValidMoves moves;

    /**
     * New no-args constructor for SudokuBoard.
     * Sets board to a new 2-dimensional array
     * Sets unsolved board to a new 2-dimensional array
     * Sets moves to a new ValidMoves object.
     */
    public SudokuBoard() {
        //Initialize class variables
        board = new int[SIZE][SIZE];
        unsolvedBoard = new int[SIZE][SIZE];
        moves = new ValidMoves(board, SIZE, unsolvedBoard);
    }

    /**
     * Generate the board
     */
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

    /**
     * Create the board
     * return true or false whether we finished creating the board or not.
     */
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


    /**
     * Gets cell value.
     *
     * @param row the row
     * @param col the col
     * @return the cell value
     */
    public int getCellValue(int row, int col) {
        return unsolvedBoard[row][col];
    }


    /**
     * Sets cell value.
     *
     * @param row   the row
     * @param col   the col
     * @param value the value
     */
    public void setCellValue(int row, int col, int value) {
        unsolvedBoard[row][col] = value;
    }

    /**
     * Sets board value.
     *
     * @param row   the row
     * @param col   the col
     * @param value the value
     */
    public void setBoardValue(int row, int col, int value)
    {
        board[row][col] = value;
    }


    /**
     * Is the cell editable?
     *
     * @param row the row
     * @param col the col
     * @return yes or no
     */
    public boolean isEditableCell(int row, int col) {
        return unsolvedBoard[row][col] != board[row][col];
    }


    /**
     * Has the player finished solving the board?
     *
     * @return yes or no
     */
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

    /**
     * Gets this instance of ValidMoves
     *
     * @return ValidMoves
     */
    public ValidMoves getMoves()
    {
        return moves;
    }

    /**
     * Get the solved board
     *
     * @return the solved board
     */
    public int[][] getBoard() {
        return board;
    }

    /**
     * Get the unsolved board
     *
     * @return the unsolved board
     */
    public int[][] getUnsolvedBoard() {
        return unsolvedBoard;
    }

    /**
     * Get size.
     *
     * @return the size
     */
    public int getSize() {
        return SIZE;
    }
}
