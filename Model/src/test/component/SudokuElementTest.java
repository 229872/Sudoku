package component;

import org.junit.jupiter.api.Test;
import pl.component.exceptions.WrongValueException;
import pl.component.model.elements.SudokuBox;
import pl.component.model.main.SudokuBoard;
import pl.component.model.algorithm.BacktrackingSudokuSolver;
import pl.component.model.elements.SudokuRow;
import pl.component.model.main.SudokuElement;
import pl.component.model.main.SudokuField;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuElementTest {
    private final SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());

    @Test
    public void hashCodeTest() {
        assertEquals(board.getRow(0).hashCode(), board.getRow(1).hashCode());
        assertEquals(board.getColumn(0).hashCode(), board.getColumn(1).hashCode());
        assertEquals(board.getBox(0, 0).hashCode(), board.getBox(3, 3).hashCode());
        try {
            board.set(0, 0, 1);
        } catch (WrongValueException e) {
            throw new RuntimeException(e);
        }
        assertNotEquals(board.getRow(0).hashCode(), board.getRow(1).hashCode());
        assertNotEquals(board.getColumn(0).hashCode(), board.getColumn(1).hashCode());
        assertNotEquals(board.getBox(0, 0).hashCode(), board.getBox(3, 3).hashCode());

    }

    @Test
    public void equalsTest() {
        assertFalse(board.getRow(0).equals(null));
        assertFalse(board.getRow(0).equals(String.class));
        SudokuRow row = board.getRow(0);
        assertEquals(row, row);
        assertEquals(board.getRow(0), board.getRow(1));

        try {
            board.set(0, 0, 1);
        } catch (WrongValueException e) {
            throw new RuntimeException(e);
        }

        assertNotEquals(board.getRow(0), board.getRow(1));
    }

    @Test
    public void toStringTest() {
        String expected = "pl.component.model.elements.";
        assertTrue(board.getBox(0, 0).toString().startsWith(expected + "SudokuBox"));
        assertTrue(board.getRow(0).toString().startsWith(expected + "SudokuRow"));
        assertTrue(board.getColumn(0).toString().startsWith(expected + "SudokuColumn"));
    }

    @Test
    public void cloneTest() {
        List<SudokuField> fields = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < fields.size(); i++) {
            fields.set(i, new SudokuField(0));
        }
        SudokuElement box = new SudokuBox(fields);
        SudokuElement clone = box.clone();
        assertEquals(box, clone);

        fields.set(0, new SudokuField(5));
        assertNotEquals(box, clone);
    }
}
