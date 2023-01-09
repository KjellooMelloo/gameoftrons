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

    private int currentPlayerID;
    private int numPlayers;
    private Color[] colors;

    private Scene scene;
    private StackPane base;
    private Map<String, Node> screens = new HashMap<>();

    public Screen(){
        this.currentPlayerID=-1;
        this.colors=new Color[]{Color.AQUA, Color.CRIMSON, Color.YELLOW, Color.LIME, Color.FUCHSIA, Color.ORANGE};
        this.base = new StackPane();
        this.scene = new Scene(base);
        screens.put("start",new StartScreen());
    }

    /**
     * Liefert die Farbe, die in der Farbtabelle unter dem Index ist, der der übergebenen SpielerID entspricht.
     * @param playerID Die SpielerID des Spielers, dessen Farbe zurückgegeben werden soll
     * @return Die gesuchte Farbe als JAVAFX-Color
     */
    protected Color getColor(int playerID){
        if(playerID > 5) throw new IllegalArgumentException("PlayerIDs can't be larger than 5");
        return colors[playerID];
    }

    public void updateCurrentPlayerID(int newPlayerID){
        if(currentPlayerID < 0) currentPlayerID = newPlayerID;
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

    public int getCurrentPlayerID() {
        return currentPlayerID;
    }

    public void setCurrentPlayerID(int currentPlayerID) {
        this.currentPlayerID = currentPlayerID;
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

    public StackPane getBase() {
        return base;
    }
}
