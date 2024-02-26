package edu.sdccd.cisc191.sudoku.client.screen.start;

import edu.sdccd.cisc191.sudoku.client.screen.game.GameScreen;
import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Start extends BorderPane implements Screen {
    FallingNumbers numbers;
    Button startButton;
    GameScreen gameScreen;

    public Start(Stage primaryStage) {
        numbers = new FallingNumbers();
        gameScreen = new GameScreen(primaryStage);
        startScreen();
    }

    @Override
    public void startScreen() {
        numbers.startScreen();

        startButton = new Button("Start");

        startButton.setOnMouseClicked(e -> {
            if(e.getButton() == MouseButton.PRIMARY)
            {
                gameScreen.startScreen();
            }
        });

        startButton.setPrefSize(250, 100);
        startButton.setFont(new Font("Impact", 25));
        startButton.setBackground(new Background(new BackgroundFill(Color.DARKSLATEGRAY, null, null)));
        startButton.setTextFill(Color.WHITESMOKE);
        startButton.setOnMouseEntered(e -> {
            ScaleTransition pop = new ScaleTransition();
            pop.setDuration(Duration.millis(500));
            pop.setToX(1.1);
            pop.setToY(1.1);
            pop.setNode(startButton);
            pop.play();
        });

        startButton.setOnMouseExited(e -> {
            ScaleTransition pop = new ScaleTransition();
            pop.setDuration(Duration.millis(500));
            pop.setToX(1);
            pop.setToY(1);
            pop.setNode(startButton);
            pop.play();
        });
        this.setBackground(new Background(new BackgroundFill(Color.DIMGRAY, null, null)));

        StackPane pane = new StackPane();

        for(int i = 0; i < numbers.getNumbers().size(); i++)
        {
            pane.getChildren().add(numbers.getNumbers().get(i));
        }

        Text text = new Text("S\tU\tD\tO\tK\tU!");
        text.setFont(Font.font("Verdana", 65));
        text.setTabSize(10);
        text.setFill(Color.ANTIQUEWHITE);

        this.setTop(pane);
        this.setBottom(text);
        this.setCenter(startButton);
    }

}
