package pl.component.model;

import pl.component.exceptions.WrongValueException;

import java.util.HashSet;
import java.util.Set;

public class SudokuBoard {
    private final int[] board = new int[81];



    public void fillBoard() {
        solve(this.board);
    }

    private int getFieldValue(int row, int col) throws WrongValueException {
        if(row >= 0 && row < getBoardSize() && col >= 0 && col < getBoardSize()) {
            return board[getBoardSize() * row + col];
        } else {
            throw new WrongValueException();
        }
    }

    private void setFieldValue(int row, int col, int value) throws WrongValueException {
        if(row < 0 || row >= getBoardSize()) {
            throw new WrongValueException("Row index is wrong");
        }
        if(col < 0 || col >= getBoardSize()) {
            throw new WrongValueException("Col index is wrong");
        }
        if(value < 0 || value > 9) {
            throw new WrongValueException("Value is wrong");
        }
        this.board[getBoardSize() * row + col] = value;
    }
    private int getBoardSize() {
        return board.length % 9;
    }

    private boolean solve(int[] board) {
        for (int row = 0; row < getBoardSize(); row++) {
            for (int column = 0; column < getBoardSize(); column++) {
                if (board[getBoardSize() * row + column] == 0) {
                    for (int k = 1; k <= getBoardSize(); k++) {
                        board[getBoardSize() * row + column] = k;
                        if (isValid(board, row, column) && solve(board)) {
                            return true;
                        }
                        board[getBoardSize() * row + column] = 0;
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValid(int[] board, int row, int column) {

        return validateRow(board,row) &&
                validateColumn(board,row,column) &&
                validateBox(board,row,column);

    }

    private boolean validateRow(int[] board, int row) {
        int start = getBoardSize() * row;
        int end = start + getBoardSize();
        int[] data = new int[getBoardSize()];

        for (int i = start, k = 0; i < end; i++, k++) {
            data[k] = board[i];
        }

        return validate(data);
    }

    private boolean validateColumn(int[] board, int row, int column) {
        int start = getBoardSize() * row + column;
        int[] data = new int[getBoardSize()];

        for (int i = start, k = 0; i < getBoardSize(); i += getBoardSize(), k++) {
            data[k] = board[i];
        }

        return validate(data);
    }

    private boolean validateBox(int[] board, int row, int column) {
        int startRow = row % 3;
        int startColumn = column % 3;
        int[] data = new int[getBoardSize()];

        for (int i = startRow, k = 0; i < 3; i++, k++) {
            for (int j = startColumn; j < startColumn + 3; j++) {
                int index = i * getBoardSize() + j;
                data[k] = board[index];
            }
        }

        return validate(data);
    }

    private boolean validate(int[] values) {
        Set<Integer> checker = new HashSet<>(9);
        for (int value : values) {
            if(!checker.add(value)) {
                return false;
            }
        }
        return true;
    }


}
