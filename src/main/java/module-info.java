module edu.sdccd.cisc191.sudoku {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.sdccd.cisc191.sudoku to javafx.fxml;
    exports edu.sdccd.cisc191.sudoku;
    exports edu.sdccd.cisc191.sudoku.board;
    opens edu.sdccd.cisc191.sudoku.board to javafx.fxml;
}