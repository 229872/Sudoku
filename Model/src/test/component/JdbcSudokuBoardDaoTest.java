package component;

import org.junit.jupiter.api.Test;
import pl.component.dao.Dao;
import pl.component.dao.JdbcSudokuBoardDao;
import pl.component.dao.SudokuBoardDaoFactory;
import pl.component.model.algorithm.BacktrackingSudokuSolver;
import pl.component.model.main.SudokuBoard;

public class JdbcSudokuBoardDaoTest {
    private SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());

    @Test
    public void createDataBaseTest() {
        try (Dao<SudokuBoard> dao = SudokuBoardDaoFactory.getJdbcDao()) {

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
