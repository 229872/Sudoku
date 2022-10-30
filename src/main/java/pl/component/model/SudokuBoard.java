package pl.component.model;

import java.util.*;

import pl.component.exceptions.WrongValueException;
import pl.component.model.algorithm.SudokuSolver;


public class SudokuBoard {
    private final List<SudokuField> board = new ArrayList<>();
    private SudokuSolver sudokuSolver;

    public SudokuBoard(SudokuSolver sudokuSolver) {
        Objects.requireNonNull(sudokuSolver);
        this.sudokuSolver = sudokuSolver;
    }

    public void solveGame() {
        sudokuSolver.solve(this);
    }

    public int get(int x, int y) throws WrongValueException {
        if (y >= 0 && y < getBoardSize() && x >= 0 && x < getBoardSize()) {
            return board.get(getBoardSize() * y + x).getFieldValue();
        } else {
            throw new WrongValueException("Wrong row or column");
        }
    }

    public void set(int x, int y, int value) throws WrongValueException {
        if (y < 0 || y >= getBoardSize()) {
            throw new WrongValueException("Wrong row, row index too small or too high");
        }
        if (x < 0 || x >= getBoardSize()) {
            throw new WrongValueException("Wrong column, column index too small or too high");
        }
        if (value >= 0 && value <= getBoardSize()) {
            board.get(getBoardSize() * y + x).setFieldValue(value);
        } else {
            throw new WrongValueException("Wrong value, value too small or too high");
        }
    }

    public int getBoardSize() {
        return board.size() / 9;
    }



    public boolean validateBoard(SudokuBoard sudokuBoard) throws WrongValueException {
        int[] board = new int[81];
        int boardSize = sudokuBoard.getBoardSize();

        int k = 0;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[k] = sudokuBoard.get(j, i);
                k++;
            }
        }

        return validateRows(board)
                && validateColumns(board)
                && validateRectangles(board);
    }

    private boolean validateRows(int[] board) {
        for (int i = 0; i < getBoardSize(); i++) {
            int[] row = new int[getBoardSize()];
            for (int j = 0; j < getBoardSize(); j++) {
                row[j] = board[getBoardSize() * i + j];
            }
            if (!verify(row)) {
                return false;
            }
        }
        return true;
    }

    private boolean validateColumns(int[] board) {
        for (int i = 0; i < getBoardSize(); i++) {
            int[] col = new int[getBoardSize()];
            for (int j = 0; j < getBoardSize(); j++) {
                col[j] = board[getBoardSize() * j + i];
            }
            if (!verify(col)) {
                return false;
            }
        }
        return true;
    }

    private boolean validateRectangles(int[] board) {
        for (int i = 0; i < getBoardSize(); i += 3) {
            for (int j = 0; j < getBoardSize(); j += 3) {
                if (!checkRectangle(board,i,j)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkRectangle(int[] board, int row, int col) {
        int[] data = new int[getBoardSize()];
        int k = 0;
        for (int i = row; i < row + 3; i++) {
            for (int j = col; j < col + 3; j++) {
                data[k] = board[getBoardSize() * i + j];
                k++;
            }
        }
        return verify(data);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SudokuBoard that = (SudokuBoard) o;
        return Arrays.equals(board, that.board);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(board);
    }
}
