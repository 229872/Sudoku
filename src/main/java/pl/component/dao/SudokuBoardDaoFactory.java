package pl.component.dao;

import pl.component.model.main.SudokuBoard;

public class SudokuBoardDaoFactory {
    private SudokuBoardDaoFactory() {}

    public static Dao<SudokuBoard> getFileDao(String fileName) {
        return new FileSudokuBoardDao(fileName);
    }
}
