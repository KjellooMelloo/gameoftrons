package de.hawh.beta3.application.game.view.Screen;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.layout.VBox;

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


        // Edit style
        inputPlayerCountField.setPrefWidth(270);
        inputPlayerCountField.setMaxWidth(270);
        Effect glow = new Glow(1.0);
        btnStart.setEffect(glow);

        // Align elements
        this.setAlignment(Pos.BOTTOM_CENTER);
        //   labelReadyText.setAlignment(Pos.BOTTOM_LEFT);
        // inputPlayerCountField.setAlignment(Pos.BOTTOM_CENTER);
        // btnStart.setAlignment(Pos.BOTTOM_RIGHT);


        //  Button Press Event Logic
        btnStart.setOnAction(event -> {
            String inputString = inputPlayerCountField.getText();
            if (inputString.equals("")) {
                System.out.println("Button was pressed");
            } else {
                int playerCount = Integer.valueOf(inputString);
                ScreenCommons.CONTROLLER.handleInputPlayerCount(playerCount);
                System.out.println("Input player count: " + playerCount);
            }

        });

        // Add Control Elements to VBox
        this.getChildren().add(labelReadyText);
        this.getChildren().add(inputPlayerCountField);
        this.getChildren().add(btnStart);

    }
}
