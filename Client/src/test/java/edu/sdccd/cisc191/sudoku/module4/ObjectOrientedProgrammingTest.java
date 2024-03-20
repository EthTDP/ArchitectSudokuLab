package edu.sdccd.cisc191.sudoku.module4;

import edu.sdccd.cisc191.sudoku.client.clickhandler.HandleClicks;
import edu.sdccd.cisc191.sudoku.client.clickhandler.LeftClickHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ObjectOrientedProgrammingTest {
    @Test
    void objectOrientedProgrammingTest() {
        Assertions.assertInstanceOf(HandleClicks.class, new LeftClickHandler());
    }
}
