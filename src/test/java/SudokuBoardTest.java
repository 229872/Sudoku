import org.junit.jupiter.api.Test;
import pl.component.exceptions.WrongValueException;
import pl.component.model.SudokuBoard;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuBoardTest {

    @Test
    public void fillBoardTest() throws WrongValueException {
        SudokuBoard sudokuBoard = new SudokuBoard();
        sudokuBoard.fillBoard();

        assertTrue(SudokuBoardTestHelper.validateBoard(sudokuBoard));
    }

    @Test
    public void fillBoardDifferentlyTest() {
        SudokuBoard board1 = new SudokuBoard();
        SudokuBoard board2 = new SudokuBoard();

        board1.fillBoard();
        board2.fillBoard();

        assertNotEquals(board1,board2);
    }

    @Test
    public void getFieldValue_WrongColumnTest() {
        SudokuBoard board = new SudokuBoard();

        assertThrows(WrongValueException.class,
                () -> board.getFieldValue(0,-1));
        WrongValueException exception =
                assertThrows(WrongValueException.class,
                () -> board.getFieldValue(0, 10));
        assertEquals("Wrong row or column", exception.getMessage());
    }

    @Test
    public void getFieldValue_WrongRowTest() {
        SudokuBoard board = new SudokuBoard();

        assertThrows(WrongValueException.class,
                () -> board.getFieldValue(-1,0));
        WrongValueException exception =
                assertThrows(WrongValueException.class,
                        () -> board.getFieldValue(10, 0));
        assertEquals("Wrong row or column", exception.getMessage());
    }

    @Test
    public void getFieldValue_PositiveTest() {
        SudokuBoard board = new SudokuBoard();

        try {
            assertEquals(0, board.getFieldValue(0,0));
        } catch (WrongValueException ignored) {

        }
    }


    private static class SudokuBoardTestHelper {

        private static boolean validateBoard(SudokuBoard sudokuBoard) throws WrongValueException {
            int[] board = new int[81];
            for (int i = 0; i < 81; i++) {
                board[i] = sudokuBoard.getFieldValue(i / 9, i % 9);
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
    }
}
