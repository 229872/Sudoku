package pl.component;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.StringConverter;
import pl.component.dao.FileSudokuBoardDao;
import pl.component.model.algorithm.BacktrackingSudokuSolver;
import pl.component.model.main.Difficulty;
import pl.component.model.main.SudokuBoard;


public class PlayGameFormController extends Window {
    private SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());

    private final SudokuBoardFx sudokuBoard =
            new SudokuBoardFx(board);
    @FXML
    private GridPane sudokuBoardGrid;

    @FXML
    private Button backButton;

    private TextField[][] textFields = new TextField[9][9];
    private final StringConverter<Number> converter = new SudokuFieldConverter();
    private final ResourceBundle bundle = ResourceBundle.getBundle("sudoku");




    public void setDifficultyLevel(Difficulty difficulty) {
        this.sudokuBoard.setDifficultyLevel(difficulty);
    }

    public void startGame() {
        sudokuBoard.solveGame();
        sudokuBoard.deleteFields();
        sudokuBoard.init();
        fillGrid();
    }

    @FXML
    public void backToMainForm() throws IOException {
        App.backToMainForm();
    }

    public void fillGrid()  {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                textFields[i][j] = new TextField("");
                Bindings.bindBidirectional(textFields[i][j].textProperty(),
                        sudokuBoard.getProperty(i, j), converter);
                if (textFields[i][j].getText().matches("[1-9]")) {
                    textFields[i][j].setDisable(true);
                    styleAlreadyInsertedField(textFields[i][j]);
                } else {
                    styleFreeToInsertField(textFields[i][j]);
                }
                textFields[i][j].setTextFormatter(new TextFormatter<>(this::filter));

                sudokuBoardGrid.add(textFields[i][j], j, i);
            }
        }
    }

    private TextFormatter.Change filter(TextFormatter.Change change) {
        if (!change.getControlNewText().matches("[1-9]?")) {
            change.setText("");
        }
        return change;
    }

    private void styleAlreadyInsertedField(TextField field) {
        field.setMaxSize(100,65);
        field.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 18));
        field.setStyle("-fx-background-color: #F0EBD7;-fx-alignment: center;"
                + "-fx-border-style: solid; -fx-opacity: 100%");
    }

    private void styleFreeToInsertField(TextField field) {
        field.setMaxSize(100,65);
        field.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 18));
        field.setStyle("-fx-background-color: #F0EBD7;-fx-alignment: center;"
                + "-fx-border-style: solid");
    }

    @FXML
    public void load(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(this);

        try (FileSudokuBoardDao dao =
                     new FileSudokuBoardDao(file.getAbsolutePath())) {
            this.board = dao.read();
            this.sudokuBoard.setSudokuBoard(board);
            this.sudokuBoard.init();
            fillGrid();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, bundle.getString("error.load"));
            alert.showAndWait();
        }

    }

    @FXML
    public void save(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(this);

        try (FileSudokuBoardDao dao =
                     new FileSudokuBoardDao(file.getAbsolutePath())) {
            dao.write(board);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, bundle.getString("error.save"));
            alert.showAndWait();
        }
    }
}