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
                    "CREATE TABLE BOARDS(BOARD_ID long PRIMARY KEY AUTO_INCREMENT,"
                    + "BOARD_NAME varchar(30) NOT NULL UNIQUE);"
                    + " CREATE TABLE FIELDS(X int NOT NULL, Y int NOT NULL, "
                    + "VALUE int NOT NULL, BOARD_ID long, FOREIGN KEY (BOARD_ID)"
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
            insertIntoBoardTable();
            long fieldId = findIdByName(this.boardName);
            insertIntoFieldsTable(fieldId, obj);
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
                    bundle.getString("FileSudokuBoardDao.exception.error.message"), e);
        }
    }

    public String getBoardName() {
        return boardName;
    }

    private void insertIntoBoardTable() throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO BOARDS (BOARD_NAME) VALUES (?)"
        )) {
            preparedStatement.setString(1, this.boardName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            connection.rollback();
        }
    }

    private void insertIntoFieldsTable(long fieldId, SudokuBoard board) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO FIELDS VALUES(?, ?, ?, ?)"
        )) {

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    preparedStatement.setInt(1, j);
                    preparedStatement.setInt(2, i);
                    preparedStatement.setInt(3, board.get(j, i));
                    preparedStatement.setLong(4, fieldId);
                }
            }

        } catch (SQLException e) {
            connection.rollback();
        }
    }

    private long findIdByName(String boardName) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT BOARD_ID FROM BOARDS WHERE BOARD_NAME=?"
        )) {
            preparedStatement.setString(1, boardName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return resultSet.getLong(1);
            } catch (Exception e) {
                connection.rollback();
            }
        } catch (SQLException e) {
            connection.rollback();
        }
        throw new SQLException();
    }
}
