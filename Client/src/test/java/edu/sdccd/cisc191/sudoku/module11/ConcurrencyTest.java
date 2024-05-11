package edu.sdccd.cisc191.sudoku.module11;

import edu.sdccd.cisc191.sudoku.client.screen.game.GameTimer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConcurrencyTest {

    @Test
    void concurrencyTest() throws InterruptedException {
        GameTimer timer = new GameTimer();
        timer.startTimer();
        Thread.sleep(1000);
        GameTimer.stopTimer();

        Assertions.assertEquals(2, GameTimer.seconds);
        GameTimer.seconds = 0;
    }
}
