module edu.sdccd.cisc191.sudoku {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.graphics;
    requires javafx.media;


    opens edu.sdccd.cisc191.sudoku.client to javafx.fxml;
    exports edu.sdccd.cisc191.sudoku.client;
    exports edu.sdccd.cisc191.sudoku.client.screen.start;
    opens edu.sdccd.cisc191.sudoku.client.screen.start to javafx.fxml;
    exports edu.sdccd.cisc191.sudoku.client.screen;
    opens edu.sdccd.cisc191.sudoku.client.screen to javafx.fxml;
}