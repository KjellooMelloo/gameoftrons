package de.hawh.beta3.application.game.view.Screen;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.layout.VBox;



public class EndScreen extends VBox {

    private final ScreenCommons screenCommons = new ScreenCommons();

    private final SimpleIntegerProperty winner;
    private final Label winnerLabel;

    public EndScreen() {
        winner = new SimpleIntegerProperty(3);
        Label gameOverLabel = new Label("END OF LINE.");
        gameOverLabel.setEffect(new Glow());
        winnerLabel = new Label("");
        winnerLabel.setId("winnerLabelText");
        styleScreen();

        this.getChildren().add(gameOverLabel);
        this.getChildren().add(winnerLabel);
        registerWinnerListener();

    }

    private void styleScreen() {
        this.getStyleClass().add("root");
        this.getStylesheets().add("end.css");
        this.setAlignment(Pos.CENTER);
    }

    public void setWinner(int winner) {
        this.winner.set(winner);
    }

    private void registerWinnerListener() {
        winner.addListener(
                e -> {
                    if (winner.get() < 0) {
                        winnerLabel.setText("The game finished as a tie.");
                    } else {
                        winnerLabel.setText("The winner is player " + screenCommons.getColorName(winner.get()));
                    }

                }
        );
    }


}

