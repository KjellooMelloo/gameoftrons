package de.hawh.beta3.application.game.view.Screen;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class LobbyScreen extends VBox {

    private final ScreenCommons screenCommons = new ScreenCommons();
    private SimpleIntegerProperty observablePlayersInLobby;
    private int currentPlayerID;
    Label playerCounterLabel;

    public LobbyScreen(int currentPlayerID, int playersInLobby){
        //Felder initialisieren
        super(20.0);
        this.currentPlayerID = currentPlayerID;
        this.observablePlayersInLobby = new SimpleIntegerProperty(playersInLobby);
        this.playerCounterLabel = new Label(this.observablePlayersInLobby.get() + " players have joined the game");


        // Kontrollelemente initialisieren
        Label titleLabel = new Label("Please wait until all players have joined the game");
        Button cancelButton = new Button("Cancel");

        // Vbox einrichten
        this.getStyleClass().add("root");
        this.getStylesheets().add("lobby.css");
        this.setAlignment(Pos.BOTTOM_CENTER);



        //Kontrollelemente Logik
        cancelButton.setOnAction(event -> {
            System.out.println("Cancel Button clicked");
            //TODO call controller interface
        });



        this.getChildren().add(titleLabel);
        this.getChildren().add(playerCounterLabel);



        if(this.currentPlayerID==0){
            this.getChildren().add(cancelButton);
        }
        this.observablePlayersInLobby.addListener(
                e-> {
                    playerCounterLabel.setText(observablePlayersInLobby.get() +" players have joined the game.");
                }
        );
    }

    public void setPlayersInLobby(int playersInLobby) {
        this.observablePlayersInLobby.set(playersInLobby);
    }

    public void setCurrentPlayerID(int currentPlayerID) {
        this.currentPlayerID = currentPlayerID;
    }
}
