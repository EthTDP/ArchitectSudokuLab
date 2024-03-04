package edu.sdccd.cisc191.sudoku.client.screen.game;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Timer;
import java.util.TimerTask;

public class GameTimer extends Text {

    public void startTimer() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            int time = 120000;

            @Override
            public void run() {
                setFont(Font.font(25));
                setText("Time Left: " + time-- + " seconds");
            }

        }, 1000,2*60*1000);
    }
}
