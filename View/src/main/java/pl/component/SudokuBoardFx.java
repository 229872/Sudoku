package pl.component;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import pl.component.model.main.Difficulty;
import pl.component.model.main.SudokuBoard;


public class SudokuBoardFx {
    private final SudokuBoard sudokuBoard;
    private IntegerProperty[][] properties;


    public SudokuBoardFx(SudokuBoard sudokuBoard) {
        this.sudokuBoard = sudokuBoard;
        this.properties = new SimpleIntegerProperty[9][9];

        for (int i = 0; i < 9; i++) {
            this.properties[i] = new SimpleIntegerProperty[9];
            for (int j = 0; j < 9; j++) {
                this.properties[i][j] = new SimpleIntegerProperty();
            }
        }
    }

    public void setDifficultyLevel(Difficulty difficultyLevel) {
        this.sudokuBoard.setDifficultyLevel(difficultyLevel);
    }

    public void solveGame() {
        this.sudokuBoard.solveGame();
    }

    public void deleteFields() {
        this.sudokuBoard.deleteFields();
    }

    public void init() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.properties[i][j].set(this.sudokuBoard.get(j, i));
            }
        }
    }

    public int get(int x, int y) {
        return this.sudokuBoard.get(x, y);
    }

    public void set(int x, int y, int value) {
        this.sudokuBoard.set(x, y, value);
    }

    public IntegerProperty getProperty(int i, int j) {
        return this.properties[i][j];
    }
}
