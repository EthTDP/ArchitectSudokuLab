package edu.sdccd.cisc191.sudoku.client.screen.start;

import edu.sdccd.cisc191.sudoku.client.files.BoardtoFile;
import edu.sdccd.cisc191.sudoku.client.networking.ClientSender;
import edu.sdccd.cisc191.sudoku.client.screen.Screen;
import edu.sdccd.cisc191.sudoku.client.screen.game.GameScreen;
import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;

public class Start extends BorderPane implements Screen {
    FallingNumbers numbers;
    Button startButton;
    GameScreen gameScreen;

    static MediaPlayer player;
    static Button originalDifficulty;
    static Button originalGamemode;
    static Stage primaryStage;

    public static String difficulty = "medium";
    public static String gamemode = "singleplayer";

    public Start(Stage primaryStage) {
        numbers = new FallingNumbers();
        Start.primaryStage = primaryStage;
        startScreen();
        playStartMusic("kahoot.mp3");
    }

    @Override
    public void startScreen() {
        numbers.startScreen();

        startButton = new Button("Start");

        Button[] difficultyButtons = new Button[3];
        Button[] gamemodeButtons = new Button[2];

        difficultyButtons[0] = new Button("Easy");
        difficultyButtons[1] = new Button("Medium");
        difficultyButtons[2] = new Button("Hard");
        gamemodeButtons[0] = new Button("singleplayer");
        gamemodeButtons[1] = new Button("multiplayer");

        originalDifficulty = difficultyButtons[1];
        originalGamemode = gamemodeButtons[0];

        Pane difficultyButtonPane = new Pane();
        Pane gamemodeButtonPane = new Pane();
        int difButtonNumb = 1;

        for(Button button : gamemodeButtons) {
            setGamemodeButtons(button, gamemodeButtons);
            if(button == gamemodeButtons[0]) {
                button.setTranslateY(150);
            } else if(button == gamemodeButtons[1]) {
                button.setTranslateY(250);
            }


            button.setOnMouseClicked(e -> {
                if(e.getButton() == MouseButton.PRIMARY) {
                    originalGamemode.setTextFill(Color.GRAY);
                    originalGamemode = button;
                    button.setTextFill(Color.BLACK);

                    if(button == gamemodeButtons[0]) {
                        gamemode = "singleplayer";
                    } else if(button == gamemodeButtons[1]){
                        gamemode = "multiplayer";
                    }
                }
            });

            setMouseHoverAnimation(button);
            gamemodeButtonPane.getChildren().add(button);
        }

        for(Button button : difficultyButtons)
        {
            setButtons(button, difficultyButtons);
            button.setTranslateY(difButtonNumb * 100);

            button.setOnMouseClicked(e -> {
                if(e.getButton() == MouseButton.PRIMARY) {
                    originalDifficulty.setTextFill(Color.GRAY);
                    originalDifficulty = button;
                    button.setTextFill(Color.BLACK);

                    if(button == difficultyButtons[0]) {
                        difficulty = "easy";
                    } else if(button == difficultyButtons[1]) {
                        difficulty = "medium";
                    } else {
                        difficulty = "hard";
                    }
                }
            });

            setMouseHoverAnimation(button);
            difficultyButtonPane.getChildren().add(button);
            difButtonNumb++;
        }

        startButton.setOnMouseClicked(e -> {
            if(e.getButton() == MouseButton.PRIMARY)
            {
                try {
                    gameScreen = new GameScreen(primaryStage);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                player.stop();
                gameScreen.startScreen();

                if(gamemode.equalsIgnoreCase("multiplayer")) {
                    try {
                        ClientSender client = new ClientSender("127.0.0.1", 4444);
                        System.out.println("Starting Server Connection!");
                    } catch (IOException ex) {
                        System.out.println("Error connecting to server!");
                    }
                }
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

        Text text = new Text("SUDOKU!");
        text.setFont(Font.font("Verdana", 65));
        text.setTabSize(10);
        text.setFill(Color.ANTIQUEWHITE);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setTranslateX(500);


        this.setTop(pane);
        this.setBottom(text);
        this.setCenter(startButton);
        this.setRight(difficultyButtonPane);
        this.setLeft(gamemodeButtonPane);
    }

    private static void setMouseHoverAnimation(Button button) {
        button.setOnMouseEntered(e -> {
            button.setBackground(button.getBackground());
            ScaleTransition pop = new ScaleTransition();
            pop.setDuration(Duration.millis(500));
            pop.setToX(1.1);
            pop.setToY(1.1);
            pop.setNode(button);
            pop.play();
        });

        button.setOnMouseExited(e -> {
            ScaleTransition pop = new ScaleTransition();
            pop.setDuration(Duration.millis(500));
            pop.setToX(1);
            pop.setToY(1);
            pop.setNode(button);
            pop.play();
        });
    }

    private static void setButtons(Button button, Button[] difficultyButtons) {
        if(button == difficultyButtons[1]) {
            button.setPrefSize(200, 100);
            button.setTextFill(Color.BLACK);
            button.setTranslateX(50);
        } else {
            button.setPrefSize(100, 100);
            button.setTextFill(Color.GRAY);
            button.setTranslateX(100);
        }

        button.setFont(Font.font("Impact", 25));
        button.setBackground(Background.EMPTY);
    }

    private static void setGamemodeButtons(Button button, Button[] gamemodeButton) {
        if(button == gamemodeButton[0]) {
            button.setTextFill(Color.BLACK);
        } else {
            button.setTextFill(Color.GRAY);
        }

        button.setTranslateX(-25);

        button.setPrefSize(200, 100);

        button.setFont(Font.font("Impact", 25));
        button.setBackground(Background.EMPTY);
    }

    private static void playStartMusic(String fileName) {
        String path = "D:\\CISC191\\Labs\\ArchitectSudokuLab\\Client\\src\\main\\java\\resources\\" + fileName;
        Media media = new Media(new File(path).toURI().toString());
        player = new MediaPlayer(media);
        player.setVolume(.10);
        player.setAutoPlay(true);
        player.play();
    }

}
