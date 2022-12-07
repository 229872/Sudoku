package pl.component;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;

    public static FXMLLoader getFxmlLoader(String fxml) {
        return new FXMLLoader(App.class.getResource(fxml));
    }

    @Override
    public void start(Stage stage) throws Exception {
        scene = new Scene(getFxmlLoader("mainForm.fxml").load(), 900, 650);
        stage.setScene(scene);
        stage.setTitle("Sudoku");
        stage.show();
    }

    static void backToMainForm() throws IOException {
        scene.setRoot(getFxmlLoader("mainForm.fxml").load());
    }

    static void setRoot(Parent p) {
        scene.setRoot(p);
    }


    public static void main(String[] args) {
        Application.launch();
    }
}
