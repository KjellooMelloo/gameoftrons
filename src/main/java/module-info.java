module de.hawh.beta3.application.game.view {
    requires javafx.controls;
    requires javafx.fxml;
    opens de.hawh.beta3.application.game.view to javafx.fxml;
    exports de.hawh.beta3.application.game.view;
}
