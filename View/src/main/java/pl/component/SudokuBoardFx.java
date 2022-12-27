package pl.component;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import pl.component.model.algorithm.BacktrackingSudokuSolver;
import pl.component.model.main.Difficulty;
import pl.component.model.main.SudokuBoard;

public class SudokuBoardFx {
    private SudokuBoard sudokuBoard;

    private IntegerProperty[] sudokuBoardProperty;

    public SudokuBoardFx() {
        sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        sudokuBoardProperty = new SimpleIntegerProperty[81];
    }

    public void setDifficultyLevel(Difficulty difficultyLevel) {
        this.sudokuBoard.setDifficultyLevel(difficultyLevel);
    }

    public void startGame() {
        this.sudokuBoard.solveGame();
        this.sudokuBoard.deleteFields();
    }

    public IntegerProperty[] getSudokuBoardProperty() {
        return sudokuBoardProperty;
    }
}
