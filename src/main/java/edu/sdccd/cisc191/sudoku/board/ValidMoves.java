package edu.sdccd.cisc191.sudoku.board;

public class ValidMoves {
    private int[][] board;
    private static int SIZE;
    private static int EMPTY_CELL;
    private int[][] unsolvedBoard;

    public ValidMoves(int[][] board, int size, int empty_cell, int[][] unsolvedBoard)
    {
        this.board = board;
        SIZE = size;
        EMPTY_CELL = empty_cell;
        this.unsolvedBoard = unsolvedBoard;
    }

    public boolean isValidMove(int row, int col, int num) {
        return !isInRow(board, row, num) && !isInColumn(board, col, num) && !isInBox(board, row - row % 3, col - col % 3, num);
    }

    public boolean canPlaceNumber(int row, int col, int num)
    {
        //row
        for(int i = 0; i < SIZE; i++)
        {
            if(unsolvedBoard[row][col] == num)
            {
                return false;
            }
        }

        return true;
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

    public boolean isValidRow(int row) {
        boolean[] seen = new boolean[SIZE];
        for (int col = 0; col < SIZE; col++) {
            int num = board[row][col];
            if (num != EMPTY_CELL) {
                if (seen[num - 1]) {
                    return false; // Duplicate found
                }
                seen[num - 1] = true;
            }
        }
        return true;
    }

    public boolean isValidColumn(int col) {
        boolean[] seen = new boolean[SIZE];
        for (int row = 0; row < SIZE; row++) {
            int num = board[row][col];
            if (num != EMPTY_CELL) {
                if (seen[num - 1]) {
                    return false; // Duplicate found
                }
                seen[num - 1] = true;
            }
        }
        return true;
    }

    public boolean isValidBox(int box) {
        int startRow = (box / 3) * 3;
        int startCol = (box % 3) * 3;
        boolean[] seen = new boolean[SIZE];
        for (int row = startRow; row < startRow + 3; row++) {
            for (int col = startCol; col < startCol + 3; col++) {
                int num = board[row][col];
                if (num != EMPTY_CELL) {
                    if (seen[num - 1]) {
                        return false; // Duplicate found
                    }
                    seen[num - 1] = true;
                }
            }
        }
        return true;
    }
}
