package edu.sdccd.cisc191.sudoku.client.screen.game;

import edu.sdccd.cisc191.sudoku.client.board.SudokuBoard;
import edu.sdccd.cisc191.sudoku.client.clickhandler.LeftClickHandler;
import edu.sdccd.cisc191.sudoku.client.clickhandler.RightClickHandler;
import edu.sdccd.cisc191.sudoku.client.files.BoardtoFile;
import edu.sdccd.cisc191.sudoku.client.screen.Screen;
import edu.sdccd.cisc191.sudoku.client.screen.start.Start;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;

import static edu.sdccd.cisc191.sudoku.client.clickhandler.LeftClickHandler.numberForSideButtons;
import static edu.sdccd.cisc191.sudoku.client.files.BoardtoFile.inputStream;

/**
 * Create a new GameScreen class which implements screen to get the "startScreen" method.
 */
public class GameScreen implements Screen {
    private static final String ROOT_DIRECTORY = "D:\\CISC191\\Labs\\ArchitectSudokuLab\\Client\\src\\main\\java\\resources";
    private static final int height = 620;
    private static final int SIZE = 9;
    private static final int width = 710;
    static LeftClickHandler leftClickHandler;
    static RightClickHandler rightClickHandler;
    static Start startScreen;
    static Stage primaryStage;
    public static SudokuBoard sudokuBoard;
    BoardtoFile file;

    /**
     * Create a new GameScreen constructor that takes in the stage so we can change it.
     *
     * @param primaryStage the primary stage
     * @throws IOException the io exception
     */
    public GameScreen(Stage primaryStage) throws IOException {
        GameScreen.primaryStage = primaryStage;
        sudokuBoard = new SudokuBoard();

        file = new BoardtoFile(ROOT_DIRECTORY, "save.txt");
    }

    @Override
    public void startScreen() {
        if(GameTimer.timer != null)
        {
            GameTimer.stopTimer();
        }
        try {
            setGameBoard();
        } catch(Exception e) {
            System.out.println("Damn");
        }
    }

    private boolean checkLoad() {
        try {
            BoardtoFile.resetInputStream();
            return inputStream != null && inputStream.available() > 0;
        } catch (IOException e) {
            return false;
        }
    }

    private void setGameBoard() throws IOException {
        if (checkLoad() && Start.gamemode.equalsIgnoreCase("singleplayer")) {
            try {
                BoardtoFile.resetInputStream();

                int[][] unsolvedBoard = readArray();
                int[][] board = readArray();


                for (int i = 0; i < unsolvedBoard.length; i++) {
                    for (int j = 0; j < unsolvedBoard[i].length; j++) {
                        sudokuBoard.setCellValue(i, j, unsolvedBoard[i][j]);
                    }
                }

                for (int i = 0; i < board.length; i++) {
                    for (int j = 0; j < board[i].length; j++) {
                        sudokuBoard.setBoardValue(i, j, board[i][j]);
                    }
                }

                System.out.print("Correct Board: \n");
                for (int[] ints : board) {
                    for (int j = 0; j < ints.length; j++) {
                        System.out.print(ints[j]);
                        System.out.print(" ");
                    }

                    System.out.print("\n");
                }

                start();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        } else {
            sudokuBoard.generateBoard();

            setUpGameScene();
        }
    }

    private int[][] readArray() throws IOException {
        int[][] array = new int[SIZE][SIZE];

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                array[i][j] = inputStream.readShort();
            }
            // Skip newline
            inputStream.readByte();
        }

        // Skip empty line
        inputStream.readByte();

        return array;
    }

    private void start() throws IOException {
        int minutes;
        int seconds;

        try {
            minutes = inputStream.readShort();
            seconds = inputStream.readShort();
        } catch (IOException exception)
        {
            minutes = 0;
            seconds = 0;
        }

        setUpGameScene(minutes, seconds);
    }

    private void setUpGameScene() throws IOException {
        setUpGameScene(0,0);
    }

    private void setUpGameScene(int minutes, int seconds) throws IOException {
        BorderPane root = new BorderPane();
        GridPane gridPane = new GridPane();
        VBox vBox = new VBox();
        Text sudoku = new Text();
        sudoku.setText("S\tU\tD\tO\tK\tU");
        sudoku.setTextAlignment(TextAlignment.CENTER);
        sudoku.setFont(Font.font("Comic Sans MS", 45));
        sudoku.setTabSize(10);

        root.setTop(sudoku);
        BorderStroke borderStroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
                new CornerRadii(5), BorderStroke.THICK, new Insets(1, 96, 59, 1));
        Border border = new Border(borderStroke);
        gridPane.setBorder(border);

        Pane bottomPane = new Pane();

        GameTimer timer = new GameTimer(minutes, seconds);
        timer.startTimer();
        timer.setTranslateY(40);

        Button saveButton = new Button();
        saveButton.setTranslateX(525);

        BackgroundFill backgroundForButtonsSolved = new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(5), new Insets(1));
        BackgroundFill backgroundForNonSolvedButtons = new BackgroundFill(Color.GHOSTWHITE, new CornerRadii(5), new Insets(1));

        saveButton.setFont(Font.font("Impact", 25));
        saveButton.setBackground(new Background(backgroundForButtonsSolved));
        saveButton.setText("Save Progress");

        bottomPane.setPrefSize(width, 50);

        bottomPane.getChildren().add(timer);
        bottomPane.getChildren().add(saveButton);

        //Set the GridPane for the side buttons
        GridPane pane = createButtonPane();

        int[][] board = new int[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Button button = new Button();
                button.setPrefSize(48, 48);
                int value = sudokuBoard.getCellValue(i, j);
                board[i][j] = value;

                button.setBackground(value!= 0 ? new Background(backgroundForButtonsSolved) : new Background(backgroundForNonSolvedButtons));

                button.setFont(new Font("Impact", 25));
                //If the value is 0, set the text to " ", if not, set it to the value.
                button.setText(value == 0 ? " " : String.valueOf(value));

                //Format Buttons
                formatButtons(i, button, j);

                button.setOnMouseEntered(e -> {
                    ScaleTransition pop = new ScaleTransition();
                    pop.setDuration(Duration.millis(500));
                    pop.setToX(1.05);
                    pop.setToY(1.05);
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
                int finalRow = i;
                int finalCol = j;

                button.setOnMouseClicked(event -> {
                    //Figuring out how to make it work with clicking and then typing.
                    leftClickHandler = new LeftClickHandler(sudokuBoard, sudokuBoard.getMoves());
                    rightClickHandler = new RightClickHandler(sudokuBoard);

                    if(event.getButton() == MouseButton.PRIMARY)
                    {
                        if(numberForSideButtons == 0)
                        {
                            leftClickHandler.handleClick(value, primaryStage, button, finalRow, finalCol);
                        } else {
                            leftClickHandler.handleSideClick(button, finalRow, finalCol);
                        }
                    } else if(event.getButton() == MouseButton.SECONDARY) {
                        if(numberForSideButtons == 0) {
                            rightClickHandler.handleClick(value, primaryStage, button, finalRow, finalCol);
                        }
                    }
                });
                gridPane.add(button, j, i);
            }
        }

        saveButton.setOnMouseClicked(e -> {
            try {
                GameTimer.stopTimer();
                file.writeBoardToFile(sudokuBoard.getUnsolvedBoard(), sudokuBoard.getBoard(), GameTimer.minutes, GameTimer.seconds);

                System.out.println("Copied board!");

                //Print to Console
                file.readBoardFromFile();
                //Print to File
                file.writeToFile(ROOT_DIRECTORY, "readable_save.txt");

//To turn screen back to Start Screen. Will show after presentation of saving.

                startScreen = new Start(primaryStage);
                startScreen.startScreen();
                Scene scene = new Scene(startScreen, 1250, 575);
                primaryStage.setScene(scene);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });

        pane.setAlignment(Pos.CENTER);

        //Formatting for cool Sudoku look! (For side buttons)
        pane.setTranslateY(125);
        pane.setTranslateX(-5);
        pane.setHgap(10);
        pane.setVgap(10);

        vBox.getChildren().add(pane);

        root.setRight(vBox);

        //GridPane for main game.
        root.setCenter(gridPane);
        root.setBackground(new Background(new BackgroundFill(Color.DIMGRAY, null, null)));

        root.setBottom(bottomPane);
        Scene scene = new Scene(root, width, height);
        scene.setFill(Color.BLACK);

        primaryStage.setScene(scene);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        primaryStage.setX((screenSize.getWidth() / 2) - ((double) width / 2));
        primaryStage.setY((screenSize.getHeight() / 2) - ((double) height / 2));

        System.out.println("Showing Scene!");
        primaryStage.show();
    }

    /**
     * Set the winner scene:
     * doesn't work rn, will fix.
     *
     * @throws IOException the io exception
     */
    public static void winner() throws IOException {
        BorderPane pane = new BorderPane();
        String serverText = Start.client.receiveFromServer();
        Text text = new Text(serverText);
        Text timeText = new Text("Time: " + GameTimer.seconds--);
        timeText.setFont(Font.font(25));
        pane.setCenter(text);
        pane.setBottom(timeText);
        Scene failScene = new Scene(pane, 400, 400);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        primaryStage.setX((screenSize.getWidth() / 2) - (200));
        primaryStage.setY((screenSize.getHeight() / 2) - (200));
        primaryStage.setScene(failScene);
    }

    private static GridPane createButtonPane() {
        int value = 1;

        GridPane pane = new GridPane();

        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                Button button = new Button();
                button.setPrefSize(48, 48);
                button.setText(String.valueOf(value));
                value++;

                BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(5), new Insets(1));
                button.setBackground(new Background(backgroundFill));

                button.setFont(new Font("Impact", 25));

                button.setOnMouseEntered(e -> {
                    ScaleTransition pop = new ScaleTransition();
                    pop.setDuration(Duration.millis(500));
                    pop.setToX(1.05);
                    pop.setToY(1.05);
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

                int finalValue = value;

                button.setOnMouseClicked(e -> {
                    if(e.getButton() == MouseButton.PRIMARY)
                    {
                        numberForSideButtons = finalValue;
                    }
                });

                pane.add(button, j, i);
            }
        }
        return pane;
    }

    private void formatButtons(int i, Button button, int j) {
        if(i == 3 || i == 6)
        {
            button.setTranslateY(3);
        }

        if (i > 2)
        {
            button.setTranslateY(3);
        }

        if(i > 5)
        {
            button.setTranslateY(6);
        }

        if(j == 3 || j == 6)
        {
            button.setTranslateX(3);
        }

        if(j > 2)
        {
            button.setTranslateX(3);
        }

        if(j > 5)
        {
            button.setTranslateX(6);
        }
    }
}
