package edu.sdccd.cisc191.sudoku.client.screen.game;

import edu.sdccd.cisc191.sudoku.client.board.SudokuBoard;
import edu.sdccd.cisc191.sudoku.client.button.LeftClickHandler;
import edu.sdccd.cisc191.sudoku.client.screen.start.Screen;
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

import static edu.sdccd.cisc191.sudoku.client.button.LeftClickHandler.numberForSideButtons;

public class GameScreen implements Screen {
    private static final int height = 575;
    private static final int SIZE = 9;
    private static final int width = 710;
    static LeftClickHandler leftClickHandler;
    static Background original;

    Stage primaryStage;
    SudokuBoard sudokuBoard;

    public GameScreen(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
    }

    @Override
    public void startScreen() {
        setGameScene(primaryStage);
    }

    private void setGameScene(Stage primaryStage) {
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
                new CornerRadii(5), BorderStroke.THICK, new Insets(1, 96, 63, 1));
        Border border = new Border(borderStroke);
        gridPane.setBorder(border);

        sudokuBoard = new SudokuBoard();
        sudokuBoard.generateBoard();

        //Set the GridPane for the side buttons
        GridPane pane = createButtonPane();

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Button button = new Button();
                button.setPrefSize(48, 48);
                int value = sudokuBoard.getCellValue(i, j);
                BackgroundFill backgroundForButtonsSolved = new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(5), new Insets(1));
                BackgroundFill backgroundForNonSolvedButtons = new BackgroundFill(Color.GHOSTWHITE, new CornerRadii(5), new Insets(1));

                if(value != 0) {
                    button.setBackground(new Background(backgroundForButtonsSolved));
                } else {
                    button.setBackground(new Background(backgroundForNonSolvedButtons));
                }

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

                original = button.getBackground();
                int finalRow = i;
                int finalCol = j;


                button.setOnMouseClicked(event -> {
                    //Figuring out how to make it work with clicking and then typing.
                    leftClickHandler = new LeftClickHandler(sudokuBoard, sudokuBoard.getMoves());

                    if(event.getButton() == MouseButton.PRIMARY)
                    {
                        if(numberForSideButtons == 0)
                        {
                            leftClickHandler.handleClick(value, primaryStage, button, finalRow, finalCol, original);
                        } else {
                            leftClickHandler.handleSideClick(button, finalRow, finalCol, original);
                        }
                    }


                    //Working with clicking after.
//                        LeftClickHandler leftClickHandler = new LeftClickHandler(sudokuBoard, textField, sudokuBoard.getMoves());
//                        RightClickHandler rightClickHandler = new RightClickHandler(sudokuBoard, textField);
//
//                        if(event.getButton() == MouseButton.PRIMARY)
//                        {
//                            if(numberForSideButtons == 0)
//                            {
//                                try {
//                                    leftClickHandler.handleClick(primaryStage, button, finalI, finalJ, textField.getText());
//                                }  catch (Exception exception) {
//                                    leftClickHandler.showAlert("You didn't put a number!", Alert.AlertType.ERROR);
//                                    textField.clear();
//                                    textField.requestFocus();
//                                }
//                            } else {
//                                leftClickHandler.handleClick(primaryStage, button, finalI, finalJ, String.valueOf(numberForSideButtons));
//                                numberForSideButtons = 0;
//                            }
//                        } else if(event.getButton() == MouseButton.SECONDARY) {
//
//                            rightClickHandler.handleClick(primaryStage, button, finalI, finalJ, "10");
//                        }
                });
                gridPane.add(button, j, i);
            }
        }
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

        Scene scene = new Scene(root, width, height);
        scene.setFill(Color.BLACK);

        primaryStage.setScene(scene);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        primaryStage.setX((screenSize.getWidth() / 2) - ((double) width / 2));
        primaryStage.setY((screenSize.getHeight() / 2) - ((double) height / 2));

        System.out.println("Showing Scene!");
        primaryStage.show();
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
