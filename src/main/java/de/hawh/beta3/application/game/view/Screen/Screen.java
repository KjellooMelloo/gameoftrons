package de.hawh.beta3.application.game.view.Screen;

import de.hawh.beta3.application.game.view.Player.Player;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.Map;

/**
 * Base Screen der View
 * Diese Klasse verwaltet alle Bildschirmanzeigen
 */
public class Screen {

    private SimpleIntegerProperty numPlayers = new SimpleIntegerProperty(2);
    private Scene scene;
    private StackPane base;
    private Rectangle gameScreenBackground = new Rectangle();
    private Map<String, Node> screens = new HashMap<>();
    private SimpleIntegerProperty currentPlayerID = new SimpleIntegerProperty(1);

    public Screen(){
        this.base = new StackPane();
        this.scene = new Scene(base);
        base.getChildren().add(gameScreenBackground);
        base.setStyle("-fx-background-color: #180b27");


        // register screen states
        screens.put("start",new StartScreen());
        screens.put("lobby", new LobbyScreen(currentPlayerID, numPlayers));
        screens.put("game", new GameScreen());
        screens.put("countdown", new CountdownScreen(5));
        screens.put("end", new EndScreen());

    }


    public void updateCurrentPlayerID(int newPlayerID){
        if(currentPlayerID.get() < 0) {
            currentPlayerID.set(newPlayerID);
        }
    }


    public void drawScreen(String screenName){
        if(!screens.keySet().contains(screenName)){
            throw new IllegalArgumentException("A screen mapped to " + screenName + " does not exist. Available Screens are " + screens.keySet());
        }

        Node screenToShow = screens.get(screenName);
        base.getChildren().add(screenToShow);
        StackPane.clearConstraints(screenToShow);
        screenToShow.setVisible(true);
    }

    public void showCountDown(){
        CountdownScreen cs = (CountdownScreen) screens.get("countdown");
        drawScreen("countdown");
        cs.startCountdown();

    }

    public void startGame(){
        GameScreen gs = (GameScreen) screens.get("game");
        drawScreen("game");
        gs.initializeGameField();
    }

    public void resetScreen() {
        for(Map.Entry<String,Node> entry : screens.entrySet()){
            entry.getValue().setVisible(false);
        }
    }



    public int getNumPlayers() {
        return numPlayers.get();
    }

    public void setNumPlayers(int numPlayers) {
       this.numPlayers.set(numPlayers);
    }

    public Scene getScene() {
        return scene;
    }

    public Map<String, Node> getScreens (){ return screens;}


    public void updateView(int gameFieldSize) {
        GameScreen gs = (GameScreen) screens.get("game");
        gs.setCurrentPlayerID(currentPlayerID.get());
        gs.setFieldSize(gameFieldSize);
        gs.setNumPlayers(numPlayers.get());
        drawScreen("game");
        gs.initializeGameField();
    }

    public void setCurrentPlayerID(int id){
        this.currentPlayerID.set(id);
    }
}
