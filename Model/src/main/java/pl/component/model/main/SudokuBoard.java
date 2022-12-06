package pl.component.model.main;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import pl.component.exceptions.WrongValueException;
import pl.component.model.algorithm.BacktrackingSudokuSolver;
import pl.component.model.algorithm.SudokuSolver;
import pl.component.model.elements.SudokuBox;
import pl.component.model.elements.SudokuColumn;
import pl.component.model.elements.SudokuRow;


public class SudokuBoard implements PropertyChangeListener, Serializable, Cloneable {
    private List<SudokuField> board;
    private SudokuSolver sudokuSolver;
    private boolean isVerified = false;

    public SudokuBoard(SudokuSolver sudokuSolver) {
        Objects.requireNonNull(sudokuSolver);
        this.sudokuSolver = sudokuSolver;
        this.board = Arrays.asList(new SudokuField[81]);

        for (int i = 0; i < 81; i++) {
            board.set(i, new SudokuField(0));
            board.get(i).addPropertyChangeListener(this);
        }
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

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    private boolean checkBoard() {
        for (int i = 0; i < getBoardSize(); i++) {
            if (!getRow(i).verify()) {
                return false;
            }
            if (!getColumn(i).verify()) {
                return false;
            }
        }
        for (int i = 0; i < getBoardSize(); i += 3) {
            for (int j = 0; j < getBoardSize(); j += 3) {
                if (!getBox(j, i).verify()) {
                    return false;
                }
            }
        }
        return true;
    }

    public SudokuRow getRow(int y) {
        return new SudokuRow(board.subList(getBoardSize() * y,
                getBoardSize() * y + getBoardSize()));
    }

    public SudokuColumn getColumn(int x) {
        List<SudokuField> column = new ArrayList<>(getBoardSize());
        for (int i = 0; i < getBoardSize(); i++) {
            column.add(new SudokuField(board.get(getBoardSize() * i + x).getFieldValue()));
        }
        return new SudokuColumn(column);
    }

    public SudokuBox getBox(int x, int y) {
        int column = x - x % 3;
        int row = y - y % 3;
        List<SudokuField> box = new ArrayList<>(getBoardSize());
        for (int i = row; i < row + 3; i++) {
            for (int j = column; j < column + 3; j++) {
                box.add(new SudokuField(board.get(getBoardSize() * i + j).getFieldValue()));
            }
        }
        return new SudokuBox(box);
    }

    public void solveGame() {
        sudokuSolver.solve(this);
    }

    public int getBoardSize() {
        return board.size() / 9;
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

        return new EqualsBuilder().append(board, that.board).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(board).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("board", board)
                .append("sudokuSolver", sudokuSolver)
                .append("isVerified", isVerified)
                .toString();
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        if (!checkBoard() && isVerified) {
            throw new RuntimeException("Wrong value: " + propertyChangeEvent.getNewValue());
        }
    }

    @Override
    public SudokuBoard clone() {
        try {
            SudokuBoard cloneBoard = (SudokuBoard) super.clone();
            cloneBoard.board = Arrays.asList(new SudokuField[81]);

            for (int i = 0; i < 81; i++) {
                cloneBoard.board.set(i, board.get(i));
                cloneBoard.board.get(i).addPropertyChangeListener(cloneBoard);
            }

            return cloneBoard;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
