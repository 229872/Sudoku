package component;

import org.junit.jupiter.api.Test;
import pl.component.dao.Dao;
import pl.component.dao.SudokuBoardDaoFactory;
import pl.component.exceptions.JdbcConnectionErrorException;
import pl.component.model.algorithm.BacktrackingSudokuSolver;
import pl.component.model.main.SudokuBoard;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JdbcSudokuBoardDaoTest {
    private SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
    private String boardName = "test";

    @Test
    public void createDataBasePositiveTest() {
        assertDoesNotThrow(() ->
                SudokuBoardDaoFactory.getJdbcDao("kompo", boardName));
    }

    @Test
    public void createDataBaseNegativeTest() {
        try (Dao<SudokuBoard> dao =
                     SudokuBoardDaoFactory.getJdbcDao("wrongOne", boardName)) {
            //ignore
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            assertEquals(e.getClass(), JdbcConnectionErrorException.class);
            assertEquals(e.getMessage(), "Could not create database");
        }
    }

    @Test
    public void readWriteTest() {
        try (Dao<SudokuBoard> dao =
                SudokuBoardDaoFactory.getJdbcDao("kompo", boardName)) {
            board.set(0, 0, 1);
            dao.write(board);

            SudokuBoard readBoard = dao.read();
            assertEquals(board, readBoard);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
