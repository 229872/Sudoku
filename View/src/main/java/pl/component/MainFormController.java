package pl.component;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import pl.component.model.main.Difficulty;

public class MainFormController {
    public Button button;
    public ChoiceBox<String> chooseDifficultyBox;


    @FXML
    public void switchToPlayGameForm() throws IOException {
        FXMLLoader loader = App.getFxmlLoader("playGameForm.fxml");
        Parent parent = loader.load();
        PlayGameFormController playGameFormController = loader.getController();
        switch (chooseDifficultyBox.getValue()) {
            case "Easy" -> playGameFormController.setDifficultyLevel(Difficulty.EASY);
            case "Medium" -> playGameFormController.setDifficultyLevel(Difficulty.MEDIUM);
            case "Hard" -> playGameFormController.setDifficultyLevel(Difficulty.HARD);
            default -> throw new RuntimeException("No option");
        }
        playGameFormController.startGame();
        App.setRoot(parent);
    }

    @FXML
    public void initialize() {
        chooseDifficultyBox.setOnAction(this::turnOnButton);
        if (chooseDifficultyBox.getValue() == null) {
            button.setDisable(true);
        }
    }

    private void turnOnButton(ActionEvent actionEvent) {
        button.setDisable(false);
    }


}
