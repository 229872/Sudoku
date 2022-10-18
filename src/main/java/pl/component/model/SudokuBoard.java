package pl.component.model;

import pl.component.exceptions.WrongValueException;

import java.util.*;

public class SudokuBoard {
    private final int[] board = new int[81];

    public void fillBoard() {
        List<Integer> randomNumbers = Arrays.asList(1,2,3,4,5,6,7,8,9);
        Collections.shuffle(randomNumbers);
        solve(this.board, randomNumbers);
    }

    public int getFieldValue(int row, int col) throws WrongValueException {
        if(row >= 0 && row < getBoardSize() && col >= 0 && col < getBoardSize()) {
            return board[getBoardSize() * row + col];
        } else {
            throw new WrongValueException("Wrong row or column");
        }
    }

    private int getBoardSize() {
        return board.length / 9;
    }

    private boolean solve(int[] board, List<Integer> randomNumbers) {

        for (int row = 0; row < getBoardSize(); row++) {
            for (int column = 0; column < getBoardSize(); column++) {
                if (board[getBoardSize() * row + column] == 0) {
                    for (Integer k : randomNumbers) {
                        board[getBoardSize() * row + column] = k;
                        if (validateBoard(board) && solve(board, randomNumbers)) {
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

    private boolean validateBoard(int[] board) {
        return validateRows(board) &&
                validateColumns(board) &&
                validateRectangles(board);
    }

    private boolean validateRows(int[] board) {
        for (int i = 0; i < getBoardSize(); i++) {
            int[] row = new int[getBoardSize()];
            for (int j = 0; j < getBoardSize(); j++) {
                row[j] = board[getBoardSize() * i + j];
            }
            if(!verify(row)) {
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
            if(!verify(col)) {
                return false;
            }
        }
        return true;
    }

    private boolean validateRectangles(int[] board) {
        for (int i = 0; i < getBoardSize(); i += 3) {
            for (int j = 0; j < getBoardSize(); j += 3) {
                if(!checkRectangle(board,i,j)) {
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

    private boolean verify(int[] values) {
        Set<Integer> checker = new HashSet<>(9);
        int zerosCounter = 0;
        for (int value : values) {
            if(value != 0) {
                checker.add(value);
            } else {
                zerosCounter++;
            }
        }
        return checker.size() == values.length - zerosCounter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SudokuBoard that = (SudokuBoard) o;
        return Arrays.equals(board, that.board);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(board);
    }
}
