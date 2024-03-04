package edu.sdccd.cisc191.sudoku.client.screen.game;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Timer;
import java.util.TimerTask;

public class GameTimer extends Text {

    static Timer timer;
    public static int seconds;
    public static int minutes;

    public GameTimer() {
        seconds = 0;
        minutes = 0;
        timer = new Timer();
    }

    public GameTimer(int minutes, int seconds) {
        GameTimer.minutes = minutes;
        GameTimer.seconds = seconds;
        timer = new Timer();
    }

    public void startTimer() {
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                if(seconds >= 60) {
                    minutes++;
                    seconds = 0;
                }
                setFont(Font.font(25));
                if(minutes == 0) {
                    setText("Time: " + seconds + " seconds");
                } else if(seconds > 9) {
                    setText("Time: " + minutes + ":" + seconds);
                } else {
                    setText("Time: " + minutes + ":0" + seconds);
                }

                seconds++;
            }

        }, 0, 1000);
    }

    public static void stopTimer() {
        timer.cancel();
    }
}