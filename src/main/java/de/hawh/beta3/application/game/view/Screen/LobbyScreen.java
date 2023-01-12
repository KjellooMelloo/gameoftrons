package de.hawh.beta3.application.game.view.Screen;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class LobbyScreen extends VBox {

    private final ScreenCommons screenCommons = new ScreenCommons();
    private int playersInLobby;
    private int currentPlayerID;
    Label playerCounterLabel;

    public LobbyScreen(SimpleIntegerProperty currentPlayerIDArg, SimpleIntegerProperty observablePlayersInLobby){
        //Felder initialisieren
        super(20.0);


        this.playersInLobby = observablePlayersInLobby.get();
        this.playerCounterLabel = new Label(this.playersInLobby + " player has joined the game");


        // Kontrollelemente initialisieren
        Label titleLabel = new Label("Please wait until all players have joined the game");
        Button cancelButton = new Button("Cancel");

        // Vbox einrichten
        styleScreen();


        //Kontrollelemente Logik
        registerCancelEventHandler(cancelButton);


        this.getChildren().add(titleLabel);
        this.getChildren().add(playerCounterLabel);


        if(this.currentPlayerID==0){
            this.getChildren().add(cancelButton);
        }

        // Add listener to ID and player count
        addPlayerCountListener(observablePlayersInLobby);
        addCurrentPlayerIDListener(currentPlayerIDArg);
    }



    private void addPlayerCountListener(SimpleIntegerProperty observablePlayersInLobby) {
        observablePlayersInLobby.addListener(
                e-> {
                    this.playersInLobby=observablePlayersInLobby.get();
                    if(observablePlayersInLobby.get()<=1){
                        playerCounterLabel.setText(playersInLobby +" player has joined the game.");
                    } else playerCounterLabel.setText(playersInLobby +" players have joined the game.");
                }
        );
    }

    private static void registerCancelEventHandler(Button cancelButton) {
        cancelButton.setOnAction(event -> {
            System.out.println("Cancel Button clicked");
            //TODO call controller interface
        });
    }

    private void styleScreen() {
        this.getStyleClass().add("root");
        this.getStylesheets().add("lobby.css");
        this.setAlignment(Pos.TOP_CENTER);
    }

    private void addCurrentPlayerIDListener(SimpleIntegerProperty currentPlayerIDArg) {
        this.currentPlayerID = currentPlayerIDArg.get();
        currentPlayerIDArg.addListener(e->{
            this.currentPlayerID = currentPlayerIDArg.get();
        });
    }


}
