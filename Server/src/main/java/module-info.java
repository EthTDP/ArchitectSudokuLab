module edu.sdccd.cisc191.sudoku {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens edu.sdccd.cisc191.sudoku.server to javafx.fxml;
    exports edu.sdccd.cisc191.sudoku.server;
}