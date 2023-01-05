package pl.component.dao;

import org.apache.commons.lang3.NotImplementedException;
import pl.component.exceptions.JDBCConnectionErrorException;
import pl.component.exceptions.WriteDatabaseException;
import pl.component.model.main.SudokuBoard;

import java.sql.*;
import java.util.ResourceBundle;


public class JdbcSudokuBoardDao implements Dao<SudokuBoard> {
    private static final String URL = "jdbc:postgresql://localhost:5432/";
    private static final String DRIVER = "org.postgresql.Driver";
    private static final ResourceBundle bundle = ResourceBundle.getBundle("bundles/exceptions");
    private final Connection connection;
    private final String boardName;

    public JdbcSudokuBoardDao(String dataBaseName, String boardName) {
        this.boardName = boardName;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL + dataBaseName, "kompo", "password");
            connection.setAutoCommit(false);
            createTables();
        } catch (ClassNotFoundException | SQLException e) {
            throw new JDBCConnectionErrorException(
                    bundle.getString("JDBCSudokuBoardDao.exception.create.message"),
                    e);
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
        try {
            insertToBoardTable();
        } catch (SQLException e) {
            throw new WriteDatabaseException(
                    bundle.getString("FileSudokuBoardDao.exception.write.message"),
                    e);
        }

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

    public String getBoardName() {
        return boardName;
    }

    private void insertToBoardTable() throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO BOARDS (BOARD_NAME) VALUES (?)"
        )) {
            preparedStatement.setString(1, this.boardName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            connection.rollback();
        }
    }
}
