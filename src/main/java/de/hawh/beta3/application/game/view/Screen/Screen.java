package de.hawh.beta3.application.game.view.Screen;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

/**
 * Base Screen der View
 * Diese Klasse verwaltet alle Bildschirmanzeigen
 */
public class Screen {

    private int numPlayers = 0;
    private int windowSize=100;
    private Scene scene;
    private StackPane base;
    private Map<String, Node> screens = new HashMap<>();
    private int currentPlayerID=-1;

    public Screen(){
        this.base = new StackPane();
        this.scene = new Scene(base);

        // register screen states
        screens.put("start",new StartScreen());
        screens.put("lobby", new LobbyScreen(currentPlayerID,numPlayers));
    }


    public void updateCurrentPlayerID(int newPlayerID){
        //if(currentPlayerID < 0) currentPlayerID = newPlayerID;
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

    public void resetScreen() {
        for(Map.Entry<String,Node> entry : screens.entrySet()){
            entry.getValue().setVisible(false);
        }
    }



    public int getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public Scene getScene() {
        return scene;
    }

    public Map<String, Node> getScreens (){ return screens;}
}
