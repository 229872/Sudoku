import org.junit.jupiter.api.Test;
import pl.component.exceptions.WrongValueException;
import pl.component.model.SudokuBoard;
import pl.component.model.algorithm.BacktrackingSudokuSolver;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuBoardTest {

    @Test
    public void fillBoardTest() throws WrongValueException {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        sudokuBoard.solveGame();

        assertTrue(SudokuBoardTestHelper.validateBoard(sudokuBoard));
    }

    @Test
    public void fillBoardDifferentlyTest() {
        SudokuBoard board1 = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBoard board2 = new SudokuBoard(new BacktrackingSudokuSolver());

        board1.solveGame();
        board2.solveGame();

        assertNotEquals(board1,board2);
    }

    @Test
    public void get_WrongColumnTest() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());

        assertThrows(WrongValueException.class,
                () -> board.get(-1,0));
        WrongValueException exception =
                assertThrows(WrongValueException.class,
                () -> board.get(9, 0));
        assertEquals("Wrong row or column", exception.getMessage());
    }

    @Test
    public void get_WrongRowTest() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());

        assertThrows(WrongValueException.class,
                () -> board.get(0,-1));
        WrongValueException exception =
                assertThrows(WrongValueException.class,
                        () -> board.get(0, 9));
        assertEquals("Wrong row or column", exception.getMessage());
    }

    @Test
    public void get_PositiveTest() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());

        try {
            assertEquals(0, board.get(0,0));
            assertEquals(0, board.get(8, 8));
        } catch (WrongValueException ignored) {

        }
    }

    @Test
    public void set_WrongRowTest() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());

        WrongValueException exception1 =
                assertThrows(WrongValueException.class,
                        () -> board.set(0, -1, 0));
        String message = "Wrong row, row index too small or too high";

        assertEquals(message, exception1.getMessage());

        WrongValueException exception2 =
                assertThrows(WrongValueException.class,
                        () -> board.set(0, 9, 0));

        assertEquals(message, exception2.getMessage());
    }

    @Test
    public void set_WrongColumnTest() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());

        WrongValueException exception1 =
                assertThrows(WrongValueException.class,
                        () -> board.set(-1, 0, 0));
        String message = "Wrong column, column index too small or too high";

        assertEquals(message, exception1.getMessage());

        WrongValueException exception2 =
                assertThrows(WrongValueException.class,
                        () -> board.set(9, 0, 0));

        assertEquals(message, exception2.getMessage());
    }

    @Test
    public void set_WrongValueTest() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());

        WrongValueException exception1 =
                assertThrows(WrongValueException.class,
                        () -> board.set(0, 0, -1));
        String message = "Wrong value, value too small or too high";

        assertEquals(message, exception1.getMessage());

        WrongValueException exception2 =
                assertThrows(WrongValueException.class,
                        () -> board.set(0, 0, 10));

        assertEquals(message, exception2.getMessage());
    }

    @Test
    public void set_PositiveLowerBoundTest() throws WrongValueException {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());

        board.set(0, 0, 0);
        assertEquals(0, board.get(0, 0));
    }

    @Test
    public void set_PositiveUpperBoundTest() throws WrongValueException {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());

        board.set(8, 8, 9);
        assertEquals(9, board.get(8, 8));
    }

    @Test
    public void equalsTest() throws WrongValueException {
        SudokuBoard board1 = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBoard board2 = new SudokuBoard(new BacktrackingSudokuSolver());

        assertFalse(board1.equals(null));
        assertFalse(board1.equals(String.class));
        assertEquals(board1, board1);
        assertEquals(board1, board2);

        board2.set(0, 0, 1);

        assertNotEquals(board1, board2);

    }

    @Test
    public void hashCodeTest() {
        SudokuBoard board1 = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBoard board2 = new SudokuBoard(new BacktrackingSudokuSolver());

        assertEquals(board1.hashCode(), board2.hashCode());
        board1.solveGame();
        board2.solveGame();
        assertNotEquals(board1.hashCode(), board2.hashCode());
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
