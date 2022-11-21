import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.component.dao.Dao;
import pl.component.dao.SudokuBoardDaoFactory;
import pl.component.exceptions.WrongValueException;
import pl.component.model.algorithm.BacktrackingSudokuSolver;
import pl.component.model.main.SudokuBoard;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class FileSudokuBoardDaoTest {
    private static SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
    public static final String fileName = "test.txt";
    private Dao<SudokuBoard> dao = SudokuBoardDaoFactory.getFileDao(fileName);

    @BeforeAll
    public static void setUp() {
        try {
            board.set(0, 0, 1);
            board.set(1, 1, 2);
        } catch (WrongValueException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    public static void clean() {
        File file = new File(fileName);
        file.delete();
    }

    @Test
    public void instanceTest() {
        dao.write(board);
        SudokuBoard deserializedBoard = dao.read();
        assertNotNull(deserializedBoard);
        try {
            assertEquals(1, deserializedBoard.get(0, 0));
            assertEquals(2, deserializedBoard.get(1, 1));
        } catch (WrongValueException e) {
            throw new RuntimeException(e);
        }
        assertEquals(deserializedBoard, board);
    }

    @Test
    public void consistencyTest() {
        dao.write(board);
        SudokuBoard deserializedBoard = dao.read();
        assertDoesNotThrow(() -> board.set(0, 0, 1));
        assertDoesNotThrow(() -> board.get(0, 0));
        assertDoesNotThrow(() -> board.getBoardSize());
        assertDoesNotThrow(() -> board.getBox(0, 0));
        assertDoesNotThrow(() -> board.getRow(0));
        assertDoesNotThrow(() -> board.getColumn(0));
        assertDoesNotThrow(() -> board.setVerified(true));
    }
}
