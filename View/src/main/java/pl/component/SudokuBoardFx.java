package pl.component;

import pl.component.model.main.Difficulty;
import pl.component.model.main.SudokuBoard;

public class SudokuBoardFx {
    private final SudokuBoard sudokuBoard;


    public SudokuBoardFx(SudokuBoard sudokuBoard) {
        this.sudokuBoard = sudokuBoard;
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

    public int get(int x, int y) {
        return this.sudokuBoard.get(x, y);
    }

    public void set(int x, int y, int value) {
        this.sudokuBoard.set(x, y, value);
    }

}
