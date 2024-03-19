package edu.sdccd.cisc191.sudoku.client.board;

/**
 * New ValidMoves class:
 * Checks if it is a valid move or not
 */
public class ValidMoves {
    private final int[][] board;
    private static int SIZE;
    private final int[][] unsolvedBoard;

    /**
     * Create a new ValidMoves constructor
     *
     * @param board         Take in a board
     * @param size          Take in the size
     * @param unsolvedBoard Take in the unsolved board
     */
    public ValidMoves(int[][] board, int size, int[][] unsolvedBoard)
    {
        this.board = board;
        SIZE = size;
        this.unsolvedBoard = unsolvedBoard;
    }

    /**
     * Is it a valid move?
     *
     * @param row the row
     * @param col the col
     * @param num the num
     * @return valid move or not aka yes or no
     */
//Check for board
    public boolean isValidMove(int row, int col, int num) {
        return !isInRow(board, row, num) && !isInColumn(board, col, num) && !isInBox(board, row - row % 3, col - col % 3, num);
    }

    /**
     * Is it valid to place in a spot on the board?
     *
     * @param row the row
     * @param col the col
     * @param num the num
     * @return yes or no
     */
//Check for unsolvedBoard
    public boolean isValidToPlace(int row, int col, int num)
    {
        return !isInRow(unsolvedBoard, row, num) && !isInColumn(unsolvedBoard, col, num) && !isInBox(unsolvedBoard, row - row % 3, col - col % 3, num);
    }

    /**
     * Is it valid to place in a row?
     *
     * @param board the board
     * @param row   the row
     * @param num   the num
     * @return yes or no
     */
    public boolean isInRow(int[][] board, int row, int num) {
        for (int col = 0; col < SIZE; col++) {
            if(board[row][col] == num)
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Is in valid to place in the column?
     *
     * @param board the board
     * @param col   the col
     * @param num   the num
     * @return yes or no
     */
    public boolean isInColumn(int[][] board, int col, int num) {
        for (int row = 0; row < SIZE; row++) {
            if(board[row][col] == num)
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Is it valid to place inside the 3 x 3 area/box
     *
     * @param board    the board
     * @param startRow the startRow
     * @param startCol the startCol
     * @param num      the num
     * @return yes or no
     */
    public boolean isInBox(int[][] board, int startRow, int startCol, int num) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if(board[row + startRow][col + startCol] == num)
                {
                    return true;
                }
            }
        }
        return false;
    }
}
