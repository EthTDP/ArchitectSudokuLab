package edu.sdccd.cisc191.sudoku.client.screen.game;

import edu.sdccd.cisc191.sudoku.client.networking.ClientSender;
import edu.sdccd.cisc191.sudoku.client.screen.CompletionScreen;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import static edu.sdccd.cisc191.sudoku.common.winner.CheckWinner.getWinnerExists;

/**
 * The type Game timer.
 */
public class GameTimer extends Text {
    static Timer timer;
    public static int seconds;
    public static int minutes;

    /**
     * Default constructor that is not used but could be.
     */
    public GameTimer() {
        seconds = 0;
        minutes = 0;
        timer = new Timer();
    }

    /**
     * Create a new arged constructor with minutes and seconds. Set the minutes and seconds of GameTimer to these minutes and seconds
     *
     * @param minutes the minutes
     * @param seconds the seconds
     */
    public GameTimer(int minutes, int seconds) {
        GameTimer.minutes = minutes;
        GameTimer.seconds = seconds;
        timer = new Timer();
    }

    /**
     * Start the timer with a thread.
     */
    public void startTimer() {
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                if(getWinnerExists()) {
                    this.cancel();
                    try {
                        GameScreen.winner();
                    } catch (IOException e) {
                        System.out.println("Problem with changing scenes: " + e.getMessage());
                    }
                } else {
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
            }

        }, 0, 1000);
    }

    /**
     * Stop the timer.
     */
    public static void stopTimer() {
        timer.cancel();
    }
}