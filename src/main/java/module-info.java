module de.hawh.beta3.application {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    opens de.hawh.beta3.application to javafx.fxml;
    exports de.hawh.beta3.application;
    exports de.hawh.beta3.application.game.controller.statemachine;
    opens de.hawh.beta3.application.game.view.Screen to javafx.fxml;
}
