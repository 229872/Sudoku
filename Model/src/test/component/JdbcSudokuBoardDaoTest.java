package component;

import org.junit.jupiter.api.Test;
import pl.component.dao.JdbcSudokuBoardDao;
import pl.component.model.algorithm.BacktrackingSudokuSolver;
import pl.component.model.main.SudokuBoard;

public class JdbcSudokuBoardDaoTest {
    private SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());

    @Test
    public void createDataBaseTest() {
        try (JdbcSudokuBoardDao dao = new JdbcSudokuBoardDao()) {
            dao.createTables();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
