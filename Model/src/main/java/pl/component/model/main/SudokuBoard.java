package pl.component.model.main;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.component.exceptions.WrongValueException;
import pl.component.model.algorithm.SudokuSolver;
import pl.component.model.elements.SudokuBox;
import pl.component.model.elements.SudokuColumn;
import pl.component.model.elements.SudokuRow;


public class SudokuBoard implements PropertyChangeListener, Serializable, Cloneable {
    private List<SudokuField> board;
    private SudokuSolver sudokuSolver;
    private Difficulty difficultyLevel;
    private boolean isVerified = false;

    private static final Logger logger = LoggerFactory.getLogger(SudokuBoard.class);
    private static final ResourceBundle bundle = ResourceBundle.getBundle("bundles/exceptions");

    public SudokuBoard(SudokuSolver sudokuSolver) {
        Objects.requireNonNull(sudokuSolver);
        this.sudokuSolver = sudokuSolver;
        this.board = Arrays.asList(new SudokuField[81]);

        for (int i = 0; i < 81; i++) {
            board.set(i, new SudokuField(0));
            board.get(i).addPropertyChangeListener(this);
        }
        logger.debug("Sudoku board created " + this);
    }

    public int get(int x, int y) throws WrongValueException {
        if (y >= 0 && y < getBoardSize() && x >= 0 && x < getBoardSize()) {
            return board.get(getBoardSize() * y + x).getFieldValue();
        } else {
            logger.debug("Wrong row or column:  column={}, row={}", x, y);
            throw new WrongValueException(bundle.getString("SudokuBoard.exceptions.get"));
        }
    }

    public void set(int x, int y, int value) throws WrongValueException {
        if (y < 0 || y >= getBoardSize()) {
            logger.debug("Wrong row, row index too small or too high:  row={}", y);
            throw new WrongValueException(bundle.getString("SudokuBoard.exceptions.set.row"));
        }
        if (x < 0 || x >= getBoardSize()) {
            logger.debug("Wrong column, column index too small or too high:  column={}", x);
            throw new WrongValueException(bundle.getString("SudokuBoard.exceptions.set.column"));
        }
        if (value >= 0 && value <= getBoardSize()) {
            board.get(getBoardSize() * y + x).setFieldValue(value);
        } else {
            logger.debug("Wrong value, value too small or too high:  value={}", value);
            throw new WrongValueException(bundle.getString("SudokuBoard.exceptions.set.value"));
        }
    }

    public Difficulty getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(Difficulty difficultyLevel) {
        logger.debug("Set difficulty level from "
                + this.difficultyLevel + " to " + difficultyLevel);
        this.difficultyLevel = difficultyLevel;
    }

    public void deleteFields() {
        difficultyLevel.deleteFields(this);
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
        logger.debug("Set verified: {}", isVerified);
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
            logger.debug("Wrong value: {}", propertyChangeEvent.getNewValue());
            throw new RuntimeException("Wrong value: " + propertyChangeEvent.getNewValue());
        }
    }

    @Override
    public SudokuBoard clone() {
        try {
            SudokuBoard cloneBoard = (SudokuBoard) super.clone();
            cloneBoard.board = Arrays.asList(new SudokuField[81]);

            for (int i = 0; i < 81; i++) {
                cloneBoard.board.set(i, new SudokuField(board.get(i).getFieldValue()));
                cloneBoard.board.get(i).addPropertyChangeListener(cloneBoard);
            }

            return cloneBoard;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
