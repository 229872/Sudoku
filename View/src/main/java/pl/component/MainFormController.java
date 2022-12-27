package pl.component;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import pl.component.model.main.Difficulty;

public class MainFormController {
    @FXML
    private Button button;

    @FXML
    private ChoiceBox<String> chooseDifficultyBox;

    @FXML
    private ComboBox<String> chooseLanguageBox;

    @FXML
    private Label authorLabel;

    private ResourceBundle bundle;
    private ResourceBundle authorBundle;


    @FXML
    public void switchToPlayGameForm() throws IOException {
        FXMLLoader loader = App.getFxmlLoader("playGameForm.fxml");
        Parent parent = loader.load();
        PlayGameFormController playGameFormController = loader.getController();

        switch (chooseDifficultyBox.getValue()) {
            case "Easy", "Łatwy" -> playGameFormController.setDifficultyLevel(Difficulty.EASY);
            case "Medium", "Normalny" -> playGameFormController.setDifficultyLevel(Difficulty.MEDIUM);
            case "Hard", "Trudny" -> playGameFormController.setDifficultyLevel(Difficulty.HARD);
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

        bundle = ResourceBundle.getBundle("sudoku");
        authorBundle = ResourceBundle.getBundle("pl.component.author.Author");
        initControlls();
        authorLabel.setText(authorBundle.getString("author"));
    }

    public void loadLanguage() {
        switch (chooseLanguageBox.getValue()) {
            case "Polish":
                changeLanguage(new Locale("pl"));
                break;
            case "English":
            default:
                changeLanguage(new Locale("en"));
                break;
        }
    }

    private void turnOnButton(ActionEvent actionEvent) {
        button.setDisable(false);
    }

    private void changeLanguage(Locale locale) {
        Locale.setDefault(locale);
        try {
            App.backToMainForm();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initControlls() {
        chooseDifficultyBox.getItems().addAll(
                bundle.getString("difficulty.easy"),
                bundle.getString("difficulty.medium"),
                bundle.getString("difficulty.hard")
        );

        chooseLanguageBox.getItems().addAll(
                bundle.getString("language.pl"),
                bundle.getString("language.eng")
        );
    }


}
