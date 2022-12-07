module View {
    requires ModelProject;
    requires javafx.controls;
    requires javafx.fxml;

    exports pl.component to javafx.graphics, javafx.fxml;

    opens pl.component to javafx.fxml;
}