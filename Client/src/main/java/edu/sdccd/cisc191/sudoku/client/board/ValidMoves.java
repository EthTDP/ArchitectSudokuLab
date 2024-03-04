package edu.sdccd.cisc191.sudoku.client.board;

public class ValidMoves {
    private final int[][] board;
    private static int SIZE;
    private final int[][] unsolvedBoard;

    public ValidMoves(int[][] board, int size, int[][] unsolvedBoard)
    {
        this.board = board;
        SIZE = size;
        this.unsolvedBoard = unsolvedBoard;
    }

    //Check for board
    public boolean isValidMove(int row, int col, int num) {
        return !isInRow(board, row, num) && !isInColumn(board, col, num) && !isInBox(board, row - row % 3, col - col % 3, num);
    }

    //Check for unsolvedBoard
    public boolean isValidToPlace(int row, int col, int num)
    {
        return !isInRow(unsolvedBoard, row, num) && !isInColumn(unsolvedBoard, col, num) && !isInBox(unsolvedBoard, row - row % 3, col - col % 3, num);
    }

    public boolean isInRow(int[][] board, int row, int num) {
        for (int col = 0; col < SIZE; col++) {
            if(board[row][col] == num)
            {
                return true;
            }
        }

        return false;
    }

    public boolean isInColumn(int[][] board, int col, int num) {
        for (int row = 0; row < SIZE; row++) {
            if(board[row][col] == num)
            {
                return true;
            }
        }

        return false;
    }

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
