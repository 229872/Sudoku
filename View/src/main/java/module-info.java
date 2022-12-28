module View {
    requires ModelProject;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;

    exports pl.component to javafx.graphics, javafx.fxml;

    opens pl.component to javafx.fxml;
    exports pl.component.author to javafx.fxml, javafx.graphics;
    opens pl.component.author to javafx.fxml;
}