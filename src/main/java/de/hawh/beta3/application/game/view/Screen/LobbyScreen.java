package de.hawh.beta3.application.game.view.Screen;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class LobbyScreen extends VBox {

    private final ScreenCommons screenCommons = new ScreenCommons();
    private SimpleIntegerProperty observablePlayersInLobby;
    private int currentPlayerID;
    Label playerCounterLabel;

    public LobbyScreen(SimpleIntegerProperty currentPlayerIDArg, int playersInLobby){
        //Felder initialisieren
        super(20.0);

        // Add listener to ID
        registerCurrentPlayerIDListener(currentPlayerIDArg);

        this.observablePlayersInLobby = new SimpleIntegerProperty(playersInLobby);
        this.playerCounterLabel = new Label(this.observablePlayersInLobby.get() + " player has joined the game");


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
        addPlayerCountListener();
    }



    private void addPlayerCountListener() {
        this.observablePlayersInLobby.addListener(
                e-> {
                    if(observablePlayersInLobby.get()<=1){
                        playerCounterLabel.setText(observablePlayersInLobby.get() +" player has joined the game.");
                    } else playerCounterLabel.setText(observablePlayersInLobby.get() +" players have joined the game.");
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

    private void registerCurrentPlayerIDListener(SimpleIntegerProperty currentPlayerIDArg) {
        this.currentPlayerID = currentPlayerIDArg.get();
        currentPlayerIDArg.addListener(e->{
            this.currentPlayerID = currentPlayerIDArg.get();
        });
    }


}
