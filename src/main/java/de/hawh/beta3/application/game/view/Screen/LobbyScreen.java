package de.hawh.beta3.application.game.view.Screen;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class LobbyScreen extends VBox {

    Label playerCounterLabel;
    Label playerColorControlLabel;
    private int playersInLobby;
    private int currentPlayerID;


    public LobbyScreen(SimpleIntegerProperty currentPlayerIDArg, SimpleIntegerProperty observablePlayersInLobby) {
        //Felder initialisieren
        super(20.0);


        this.playersInLobby = observablePlayersInLobby.get();
        this.playerCounterLabel = new Label(this.playersInLobby + " already joined");


        // Kontrollelemente initialisieren
        Label titleLabel = new Label("Please wait until all players have joined the game...");
        titleLabel.setId("title");
        Button cancelButton = new Button("Cancel");
        playerColorControlLabel = new Label("");
        playerColorControlLabel.setId("colorLabel");


        // Vbox einrichten
        styleScreen();


        //Kontrollelemente Logik
        registerCancelEventHandler(cancelButton);


        this.getChildren().add(titleLabel);
        this.getChildren().add(playerCounterLabel);
        this.getChildren().add(playerColorControlLabel);
        //this.getChildren().add(playerControlsLabel);


        if (this.currentPlayerID == 0) {
            this.getChildren().add(cancelButton);
        }

        // Add listener to ID and player count
        addPlayerCountListener(observablePlayersInLobby);
        addCurrentPlayerIDListener(currentPlayerIDArg);
        animateBackground();
    }

    private static void registerCancelEventHandler(Button cancelButton) {
        cancelButton.setOnAction(event -> {
            System.out.println("Cancel Button clicked");
            // TODO call controller interface handleWaitingButtonClick()
        });
    }

    private void addPlayerCountListener(SimpleIntegerProperty observablePlayersInLobby) {
        observablePlayersInLobby.addListener(
                e -> {
                    this.playersInLobby = observablePlayersInLobby.get();
                    playerCounterLabel.setText(playersInLobby + " already joined.");
                }
        );
    }

    private void styleScreen() {
        this.getStyleClass().add("root");
        this.getStylesheets().add("lobby.css");
        this.setAlignment(Pos.BASELINE_CENTER);
    }

    private void addCurrentPlayerIDListener(SimpleIntegerProperty currentPlayerIDArg) {
        this.currentPlayerID = currentPlayerIDArg.get();
        currentPlayerIDArg.addListener(e -> {
            this.currentPlayerID = currentPlayerIDArg.get();
            playerColorControlLabel.setText("Your player color is " + ScreenCommons.getColorName(currentPlayerID)
                    + " and your controls are " + ScreenCommons.getPlayerControls(currentPlayerID));
            String playerColorHex = ScreenCommons.getColor(currentPlayerID).toString().substring(2, 8);
            playerColorControlLabel.setStyle("-fx-text-fill: " + "#" + playerColorHex + ";");
        });
    }

    public void animateBackground() {
        DoubleProperty xPosition = new SimpleDoubleProperty(0);
        xPosition.addListener((observable, oldValue, newValue) -> setBackgroundPositions(this, xPosition.get()));
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(xPosition, 0)),
                new KeyFrame(Duration.seconds(200), new KeyValue(xPosition, -15000))
        );
        timeline.play();
    }

    void setBackgroundPositions(VBox node, double xPosition) {
        String style = "-fx-background-position: " +
                "left " + xPosition / 6 + "px bottom," +
                "left " + xPosition / 5 + "px bottom," +
                "left " + xPosition / 4 + "px bottom," +
                "left " + xPosition / 3 + "px bottom," +
                "left " + xPosition / 2 + "px bottom," +
                "left " + xPosition + "px bottom;";
        node.setStyle(style);
    }

}
