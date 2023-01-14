module de.hawh.beta3.application.game.view {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    opens de.hawh.beta3.application.game.view to javafx.fxml;
    exports de.hawh.beta3.application.game.view;
    exports de.hawh.beta3.application.game.view.Screen;
    exports de.hawh.beta3.application;
    opens de.hawh.beta3.application.game.view.Screen to javafx.fxml;
}
