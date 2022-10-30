import org.junit.jupiter.api.Test;
import pl.component.exceptions.WrongValueException;
import pl.component.model.SudokuBoard;
import pl.component.model.algorithm.BacktrackingSudokuSolver;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuBoardTest {
    private final SudokuBoard board1 = new SudokuBoard(new BacktrackingSudokuSolver());
    private final SudokuBoard board2 = new SudokuBoard(new BacktrackingSudokuSolver());

    @Test
    public void fillBoardTest() throws WrongValueException {
        board1.solveGame();

        assertTrue(SudokuBoardTestHelper.validateBoard(board1));
    }

    @Test
    public void fillBoardDifferentlyTest() {
        board1.solveGame();
        board2.solveGame();

        assertNotEquals(board1, board2);
    }

    @Test
    public void get_WrongColumnTest() {

        assertThrows(WrongValueException.class,
                () -> board1.get(-1,0));
        WrongValueException exception =
                assertThrows(WrongValueException.class,
                () -> board1.get(9, 0));
        assertEquals("Wrong row or column", exception.getMessage());
    }

    @Test
    public void get_WrongRowTest() {

        assertThrows(WrongValueException.class,
                () -> board1.get(0,-1));
        WrongValueException exception =
                assertThrows(WrongValueException.class,
                        () -> board1.get(0, 9));
        assertEquals("Wrong row or column", exception.getMessage());
    }

    @Test
    public void get_PositiveTest() {
        try {
            assertEquals(0, board1.get(0,0));
            assertEquals(0, board1.get(8, 8));
        } catch (WrongValueException ignored) {

        }
    }

    @Test
    public void set_WrongRowTest() {

        WrongValueException exception1 =
                assertThrows(WrongValueException.class,
                        () -> board1.set(0, -1, 0));
        String message = "Wrong row, row index too small or too high";

        assertEquals(message, exception1.getMessage());

        WrongValueException exception2 =
                assertThrows(WrongValueException.class,
                        () -> board1.set(0, 9, 0));

        assertEquals(message, exception2.getMessage());
    }

    @Test
    public void set_WrongColumnTest() {

        WrongValueException exception1 =
                assertThrows(WrongValueException.class,
                        () -> board1.set(-1, 0, 0));
        String message = "Wrong column, column index too small or too high";

        assertEquals(message, exception1.getMessage());

        WrongValueException exception2 =
                assertThrows(WrongValueException.class,
                        () -> board1.set(9, 0, 0));

        assertEquals(message, exception2.getMessage());
    }

    @Test
    public void set_WrongValueTest() {

        WrongValueException exception1 =
                assertThrows(WrongValueException.class,
                        () -> board1.set(0, 0, -1));
        String message = "Wrong value, value too small or too high";

        assertEquals(message, exception1.getMessage());

        WrongValueException exception2 =
                assertThrows(WrongValueException.class,
                        () -> board1.set(0, 0, 10));

        assertEquals(message, exception2.getMessage());
    }

    @Test
    public void set_PositiveLowerBoundTest() throws WrongValueException {
        board1.set(0, 0, 0);
        assertEquals(0, board1.get(0, 0));
    }

    @Test
    public void set_PositiveUpperBoundTest() throws WrongValueException {
        board1.set(8, 8, 9);
        assertEquals(9, board1.get(8, 8));
    }

    @Test
    public void equalsTest() throws WrongValueException {
        assertFalse(board1.equals(null));
        assertFalse(board1.equals(String.class));
        assertEquals(board1, board1);
        assertEquals(board1, board2);

        board2.set(0, 0, 1);

        assertNotEquals(board1, board2);

    }

    @Test
    public void hashCodeTest() {
        assertEquals(board1.hashCode(), board2.hashCode());
        board1.solveGame();
        board2.solveGame();
        assertNotEquals(board1.hashCode(), board2.hashCode());
    }

    @Test
    public void listenerTest() {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(byteArray);
        PrintStream old = System.out;
        System.setOut(out);

        board1.setVerified(true);
        try {
            board1.set(0, 0, 3);
            board1.set(1, 0, 4);
            board1.set(0, 1, 5);
            assertEquals("", byteArray.toString());
            board1.set(3, 0, 3);
            assertEquals("Wrong value: 3\n", byteArray.toString());
        } catch (WrongValueException e) {
            throw new RuntimeException(e);
        }
        System.setOut(old);
    }


    private static class SudokuBoardTestHelper {

        private static boolean validateBoard(SudokuBoard sudokuBoard) throws WrongValueException {
            int[] board = new int[81];
            for (int i = 0; i < 81; i++) {
                board[i] = sudokuBoard.get(i % 9, i / 9);
            }
            return validateRows(board,9) &&
                    validateColumns(board,9) &&
                    validateRectangles(board, 9);
        }

        private static boolean validateRows(int[] board, int boardSize) {
            for (int i = 0; i < boardSize; i++) {
                int[] row = new int[boardSize];
                for (int j = 0; j < boardSize; j++) {
                    row[j] = board[boardSize * i + j];
                }
                if(!verify(row)) {
                    return false;
                }
            }
            return true;
        }

        private static boolean validateColumns(int[] board, int boardSize) {
            for (int i = 0; i < boardSize; i++) {
                int[] col = new int[boardSize];
                for (int j = 0; j < boardSize; j++) {
                    col[j] = board[boardSize * j + i];
                }
                if(!verify(col)) {
                    return false;
                }
            }
            return true;
        }

        private static boolean validateRectangles(int[] board, int boardSize) {
            for (int i = 0; i < boardSize; i += 3) {
                for (int j = 0; j < boardSize; j += 3) {
                    if(!checkRectangle(board,i,j,boardSize)) {
                        return false;
                    }
                }
            }
            return true;
        }

        private static boolean checkRectangle(int[] board, int row, int col, int boardSize) {
            int[] data = new int[boardSize];
            int k = 0;
            for (int i = row; i < row + 3; i++) {
                for (int j = col; j < col + 3; j++) {
                    data[k] = board[boardSize * i + j];
                    k++;
                }
            }
            return verify(data);
        }

        private static boolean verify(int[] values) {
            Set<Integer> checker = new HashSet<>(9);

            for (int value : values) {
                if(value == 0) {
                    return false;
                }
                if(!checker.add(value)) {
                    return false;
                }
            }
            return true;
        }
    }
}
