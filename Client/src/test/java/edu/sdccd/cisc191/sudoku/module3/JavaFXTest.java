package edu.sdccd.cisc191.sudoku.module3;

import edu.sdccd.cisc191.sudoku.client.SudokuApp;
import edu.sdccd.cisc191.sudoku.client.screen.game.GameScreen;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JavaFXTest {

    @Test
    void checkJavaFX() {
        Assertions.assertEquals(620, GameScreen.getHeight());
        Assertions.assertEquals(710, GameScreen.getWidth());
    }
}
