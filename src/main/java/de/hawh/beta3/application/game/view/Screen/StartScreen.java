package de.hawh.beta3.application.game.view.Screen;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class StartScreen extends VBox {


    public StartScreen() {

        //Initialize VBox
        super(20.0);
        this.getStyleClass().add("root");
        this.getStylesheets().add("start.css");


        // initialize control elements
        Label labelReadyText = new Label("Are you ready to enter a new reality?");
        TextField inputPlayerCountField = new TextField("Player Count between 2-6");
        Button btnStart = new Button("Start Game");
        ToggleButton toggleRemote = new ToggleButton("online mode");


        // Edit style
        inputPlayerCountField.setPrefWidth(270);
        inputPlayerCountField.setMaxWidth(270);
        Effect glow = new Glow(1.0);
        btnStart.setEffect(glow);
        labelReadyText.setEffect(glow);

        // Align elements
        this.setAlignment(Pos.BOTTOM_CENTER);


        //  Button Press Event Logic
        registerStartButtonEventHandler(inputPlayerCountField, btnStart);

        registerToggleButtonEventHandler(toggleRemote);

        // Add Control Elements to VBox
        this.getChildren().add(labelReadyText);
        this.getChildren().add(inputPlayerCountField);
        this.getChildren().add(btnStart);
        this.getChildren().add(toggleRemote);

    }

    private static void registerToggleButtonEventHandler(ToggleButton toggleRemote) {
        toggleRemote.setOnAction(event -> {
            toggleRemote.setText(toggleRemote.getText().equals("online mode") ?
                    "offline mode": "online mode");

            //TODO call controller interface
        });
    }

    private void registerStartButtonEventHandler(TextField inputPlayerCountField, Button btnStart) {
        btnStart.setOnAction(event -> {
            String inputString = inputPlayerCountField.getText();
            if (inputString.equals("")) {
                inputPlayerCountField.setText("Please input");
            } else {
                int playerCount = Integer.valueOf(inputString);
                try {
                    ScreenCommons sc = new ScreenCommons();
                    sc.controller.handleInputPlayerCount(playerCount);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Input player count: " + playerCount);
            }

        });
    }
}
