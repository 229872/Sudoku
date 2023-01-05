package pl.component.dao;

import org.apache.commons.lang3.NotImplementedException;
import pl.component.exceptions.JDBCConnectionErrorException;
import pl.component.model.main.SudokuBoard;

import java.sql.*;
import java.util.ResourceBundle;


public class JdbcSudokuBoardDao implements Dao<SudokuBoard> {
    private static final String URL = "jdbc:postgresql://localhost:5432/kompo";
    private static final String DRIVER = "org.postgresql.Driver";
    private static final ResourceBundle bundle = ResourceBundle.getBundle("bundles/exceptions");
    private final Connection connection;

    public JdbcSudokuBoardDao() {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, "kompo", "password");
            connection.setAutoCommit(false);
            createTables();
        } catch (ClassNotFoundException | SQLException e) {
            throw new JDBCConnectionErrorException(
                    bundle.getString("exception.create"), e);
        }
    }

    private void createTables() throws SQLException {
        try (Statement jdbcStatement = connection.createStatement()) {
            String boardTables =
                    "CREATE TABLE BOARDS(BOARD_ID int PRIMARY KEY AUTO_INCREMENT,"
                    + "BOARD_NAME varchar(30) NOT NULL UNIQUE);"
                    + " CREATE TABLE FIELDS(X int NOT NULL, Y int NOT NULL, "
                    + "VALUE int NOT NULL, BOARD_ID int, FOREIGN KEY (BOARD_ID)"
                    + " REFERENCES BOARDS (BOARD_ID) ON"
                    + " UPDATE CASCADE ON DELETE CASCADE )";
            jdbcStatement.executeUpdate(boardTables);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
        }
    }

    @Override
    public SudokuBoard read() {
        throw new NotImplementedException();
    }

    @Override
    public void write(SudokuBoard obj) {
        throw new NotImplementedException();
    }

    @Override
    public void close() throws Exception {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new Exception(
                    bundle.getString("FileSudokuBoardDao.exception.write.message"), e);
        }
    }
}
