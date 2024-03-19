package edu.sdccd.cisc191.sudoku.common;

public class PlayerID {
    private static int playerID;


    public PlayerID() {

    }

    public static int getPlayerID() {
        return playerID;
    }

    public static void setPlayerID(int playerID) {
        PlayerID.playerID = playerID;
    }
}
