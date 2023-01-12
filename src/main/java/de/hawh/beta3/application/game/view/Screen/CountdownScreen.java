package de.hawh.beta3.application.game.view.Screen;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;


public class CountdownScreen extends VBox {

    private Timeline timeline;
    private Label countdownLabel = new Label();

    private SimpleIntegerProperty countdownState;
    private int countdownLength;


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


        if (timeline != null) {
            timeline.stop();
        }
        countdownState.set(countdownLength);
        timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(countdownLength + 1),
                        new KeyValue(countdownState, 0)));
        timeline.playFromStart();


    }

}
