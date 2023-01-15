package de.hawh.beta3.application.game.view.Screen;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;


public class CountdownScreen extends VBox {

    private final Label countdownLabel = new Label();
    private final SimpleIntegerProperty countdownState;
    private final int countdownLength;
    private Timeline timeline;
    private ScreenCommons screenCommons = new ScreenCommons();


    public CountdownScreen(int countdownLength) {
        styleScreen();
        this.countdownLength = countdownLength;
        countdownState = new SimpleIntegerProperty(countdownLength);
        countdownLabel.setText("" + countdownLength);
        countdownLabel.textProperty().bind(countdownState.asString());
        this.getChildren().add(countdownLabel);
    }

    private void styleScreen() {
        this.getStyleClass().add("root");
        this.getStylesheets().add("countdown.css");
        this.setAlignment(Pos.TOP_CENTER);
    }


    public void startCountdown() {

        EventHandler<ActionEvent> onFinished = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ScreenCommons.CONTROLLER.notifyCountdownOver();
            }
        };

        if (timeline != null) {
            timeline.stop();
        }
        countdownState.set(countdownLength);
        timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(countdownLength + 1), onFinished,
                        new KeyValue(countdownState, 0)));
        timeline.playFromStart();
    }

}
