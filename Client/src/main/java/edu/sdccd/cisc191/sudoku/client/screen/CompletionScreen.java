package edu.sdccd.cisc191.sudoku.client.screen;

import edu.sdccd.cisc191.sudoku.client.networking.ClientSender;
import edu.sdccd.cisc191.sudoku.client.screen.game.GameTimer;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.awt.*;

/**
 * The class CompletionScreen which shows the screen that you have completed
 */
public class CompletionScreen {

    /**
     * Start screen which is a border pane.
     *
     * @return the border pane
     */
    public BorderPane startScreen() {
        BorderPane pane = new BorderPane();
        Text text = new Text("CONGRATULATIONS! YOU SOLVED THE PUZZLE!!!");
        Text timeText = new Text("Time: " + (GameTimer.seconds - 1 > 9 ? GameTimer.minutes + ":" + (GameTimer.seconds - 1) : GameTimer.minutes + ":0" + (GameTimer.seconds--)));
        pane.setCenter(text);
        pane.setBottom(timeText);

        return pane;
    }
}
