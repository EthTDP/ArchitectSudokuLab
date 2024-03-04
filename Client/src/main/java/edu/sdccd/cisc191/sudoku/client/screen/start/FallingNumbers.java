package edu.sdccd.cisc191.sudoku.client.screen.start;

import edu.sdccd.cisc191.sudoku.client.screen.Screen;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class FallingNumbers implements Screen {
    ArrayList<Node> numbers;
    Random number;

    @Override
    public void startScreen() {
        number = new Random();
        numbers = new ArrayList<>();

        int max = 35;
        for(int i = 0; i < max; i++)
        {
            Node box = createNewNumber(i, max);
            numbers.add(box);
            startTransition(box);
        }
    }

    private void startTransition(Node move) {
        TranslateTransition transition = new TranslateTransition();
        transition.setToY(550);
        int seconds = number.nextInt(10);
        transition.setDuration(Duration.seconds(seconds + 3));
        transition.setNode(move);
        transition.setAutoReverse(true);
        transition.setCycleCount(Timeline.INDEFINITE);
        transition.playFromStart();
    }

    private Node createNewNumber(int id, int half) {
        StackPane node = new StackPane();
        int randomNumber = number.nextInt(9);
        int max;
        int min;
        if(id > half / 2) {
            max = 660;
            min = 0;
        } else {
            max = 0;
            min = -660;
        }
        int randomWidth = number.nextInt(max - min + 1) + min;

        Rectangle rectangle = new Rectangle(48, 48);
        int randomForColor = number.nextInt(2);
        rectangle.setFill(randomForColor == 0 ? Color.LIGHTGRAY : Color.GHOSTWHITE);
        Text numberText = new Text(String.valueOf(randomNumber + 1));
        numberText.setFont(Font.font("Impact", 25));
        node.getChildren().addAll(rectangle, numberText);
        node.setTranslateX(randomWidth);
        int randomStart = number.nextInt(100);
        node.setTranslateY(-randomStart);

        return node;
    }

    public ArrayList<Node> getNumbers() {
        return numbers;
    }
}
