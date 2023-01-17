package de.hawh.beta3.application.game.view.Screen;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

/**
 * Base Screen der View
 * Diese Klasse verwaltet alle Bildschirmanzeigen
 */
public class ScreenManager {

    private final SimpleIntegerProperty numPlayers = new SimpleIntegerProperty(0);
    private final Scene scene;
    private final StackPane base;
    private final Map<String, Node> screens = new HashMap<>();
    private final SimpleIntegerProperty currentPlayerID = new SimpleIntegerProperty(-1);


    public ScreenManager() {
        this.base = new StackPane();
        this.scene = new Scene(base);
        Rectangle gameScreenBackground = new Rectangle();
        base.getChildren().add(gameScreenBackground);
        base.setStyle("-fx-background-color: #180b27");

        // register screen states
        screens.put("start", new StartScreen());
        screens.put("lobby", new LobbyScreen(currentPlayerID, numPlayers));
        screens.put("game", new GameScreen());
        screens.put("countdown", new CountdownScreen(5));
        screens.put("end", new EndScreen());

    }


    public void updateCurrentPlayerID(int newPlayerID) {
        if (currentPlayerID.get() < 0) {
            currentPlayerID.set(newPlayerID);
        }
        System.out.println("Current player number: " + numPlayers.get());
        System.out.println("Current player id:" + currentPlayerID.get());
    }


    public void drawScreen(String screenName) {
        if (!screens.containsKey(screenName)) {
            throw new IllegalArgumentException("A screen mapped to " + screenName + " does not exist. Available Screens are " + screens.keySet());
        }

        resetScreen();
        Node screenToShow = screens.get(screenName);
        if(!base.getChildren().contains(screenToShow))
        {
            base.getChildren().add(screenToShow);
        }

        StackPane.clearConstraints(screenToShow);
        screenToShow.setVisible(true);
        if(screenName.equals("end")){
            try {
                Thread.sleep(3000);
                System.exit(0);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void showCountDown() {
        CountdownScreen cs = (CountdownScreen) screens.get("countdown");
        drawScreen("countdown");
        cs.startCountdown();

    }

    public void resetScreen() {
        for (Map.Entry<String, Node> entry : screens.entrySet()) {
            entry.getValue().setVisible(false);
        }
    }


    public void setNumPlayers(int numPlayers) {
        this.numPlayers.set(numPlayers);
    }

    public Scene getScene() {
        return scene;
    }

    public Map<String, Node> getScreens() {
        return screens;
    }


    public void updateView(int playerCount, int gameFieldSize) {
        System.out.println("Player count from Controller: " + playerCount);
        if(numPlayers.get() != playerCount){
            numPlayers.set(playerCount);
        }
        GameScreen gs = (GameScreen) screens.get("game");
        gs.setCurrentPlayerID(currentPlayerID.get());
        gs.setFieldSize(gameFieldSize);
        drawScreen("game");
        gs.initializeGameField(numPlayers.get());
    }

    public void updatePlayer(int playerID, int newX, int newY, int orientation) {
        System.out.println("pID:" + playerID);
        if (playerID > numPlayers.get()-1) {
            throw new IllegalArgumentException("A player by the given ID doesn't exist");
        }
        if (orientation > 3) {
            throw new IllegalArgumentException("The orientation must be between 0-3");
        }
        String orientationAsString = "";
        switch (orientation) {
            case (0):
                orientationAsString = "LEFT";
                break;
            case (1):
                orientationAsString = "UP";
                break;
            case (2):
                orientationAsString = "RIGHT";
                break;
            case (3):
                orientationAsString = "DOWN";
                break;
        }

        GameScreen gameScreen = (GameScreen) screens.get("game");
        gameScreen.updatePlayer(playerID, newX, newY, orientationAsString);

    }

    public void endGame(int result) {
        EndScreen endScreen = (EndScreen) screens.get("end");
        endScreen.setWinner(result);
    }

    public void informUser(String information) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        ((Stage) alert.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);
        alert.setContentText(information);
        alert.show();
    }

}
