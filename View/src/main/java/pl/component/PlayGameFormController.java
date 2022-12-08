package pl.component;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import pl.component.exceptions.WrongValueException;
import pl.component.model.algorithm.BacktrackingSudokuSolver;
import pl.component.model.main.Difficulty;
import pl.component.model.main.SudokuBoard;


public class PlayGameFormController {

    private final SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
    public GridPane sudokuBoardGrid;
    public Button backButton;

    public void setDifficultyLevel(Difficulty difficulty) {
        this.sudokuBoard.setDifficultyLevel(difficulty);
    }

    public void startGame() {
        sudokuBoard.solveGame();
        sudokuBoard.deleteFields();
        fillGrid();
    }

    @FXML
    public void backToMainForm() throws IOException {
        App.backToMainForm();
    }

    public void fillGrid()  {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                TextField textField = new TextField();
                try {
                    if (sudokuBoard.get(j, i) != 0) {
                        textField.setDisable(true);
                        setHiddenField(textField);
                        textField.setText(String.valueOf(sudokuBoard.get(j,i)));
                    } else {
                        setVisibleField(textField);
                    }
                    textField.setOnKeyPressed(e -> {
                        if (e.getText().matches("[1-9]")) {
                            textField.setText(e.getText());
                        }
                    });
                    textField.setTextFormatter(new TextFormatter<>(this::filter));

                    textField.textProperty().addListener((observableValue, s, t1) -> {
                        try {
                            sudokuBoard.set(GridPane.getRowIndex(textField),
                                    GridPane.getColumnIndex(textField),
                                    Integer.parseInt(t1));
                        } catch (WrongValueException e) {
                            try {
                                sudokuBoard.set(GridPane.getRowIndex(textField),
                                        GridPane.getColumnIndex(textField),
                                        0
                                        );
                            } catch (WrongValueException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    });
                    sudokuBoardGrid.add(textField, j, i);

                } catch (WrongValueException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private TextFormatter.Change filter(TextFormatter.Change change) {
        if (!change.getControlNewText().matches("[1-9]?")) {
            change.setText("");
        }
        return change;
    }

    private void setHiddenField(TextField field) {
        field.setMaxSize(100,65);
        field.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 18));
        field.setStyle("-fx-background-color: #F0EBD7;-fx-alignment: center;"
                + "-fx-border-style: solid; -fx-opacity: 100%");
    }

    private void setVisibleField(TextField field) {
        field.setMaxSize(100,65);
        field.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 18));
        field.setStyle("-fx-background-color: #F0EBD7;-fx-alignment: center;"
                + "-fx-border-style: solid");
    }
}
