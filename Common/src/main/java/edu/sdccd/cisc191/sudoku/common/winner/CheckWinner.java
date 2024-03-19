package edu.sdccd.cisc191.sudoku.common.winner;

public class CheckWinner {
    private static boolean winnerExists;

    public static boolean getWinnerExists() {
        return winnerExists;
    }

    public static void setWinnerExists(boolean winnerExists) {
        CheckWinner.winnerExists = winnerExists;
    }
}
