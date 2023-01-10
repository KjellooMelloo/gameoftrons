package de.hawh.beta3.application.game.view.Screen;

import de.hawh.beta3.application.game.view.Player.Coordinate;
import de.hawh.beta3.application.game.view.Player.Player;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GameScreen extends Canvas {

    private int currentPlayerID=-1;
    private int fieldSize=40;
    private int[][] gameField = new int[fieldSize][fieldSize];

    private Map<Integer, Player> playerMap = new HashMap<>();
    private int windowSize = 800;
    private int initialNumPlayers = 2;
    private ScreenCommons screenCommons = new ScreenCommons();
    private Color backgroundColor = Color.BLUEVIOLET.darker().darker().darker().desaturate();


    public GameScreen(int currentPlayerID, int initialNumPlayers, int fieldSize){
        this.currentPlayerID = currentPlayerID;
        this.initialNumPlayers = initialNumPlayers;
        this.fieldSize = 10;
        this.gameField = new int[fieldSize][fieldSize];


        this.setWidth(windowSize);
        this.setHeight(windowSize);

        for(int i=0; i < initialNumPlayers; i++){
            playerMap.put(i,new Player(i));
        }


        this.getGraphicsContext2D().strokeText("Playing", 150, 20);
        initializeGameField();

    }

    private void initializeGameField() {
        GraphicsContext g = this.getGraphicsContext2D();
        g.setFill(backgroundColor);

        // Comment after tests are over
        prepareTest();
        for(Player p:playerMap.values()){
            drawTileColors(p);
        }
    }

    public GameScreen(){}

    private void drawPlayers(){}
    private void updatePlayer(int playerID,int newX, int newY, int newOrientation){}
    private void removeTileColors(int playerID){}
    private void updateTileColors(int playerID, int oldx, int oldY, int difX, int difY, String oldOrientation){}
    private void drawTileColors(Player playerToDraw){
        if(playerToDraw == null || playerToDraw.getTrail()==null){
            throw new NullPointerException();
        }
        for(Coordinate pos : playerToDraw.getTrail()){
            if(pos.x < 0 || pos.x >= fieldSize){
                throw new IllegalArgumentException("x value out of bounds: x is " + pos.x + ", but should be 0 <= x < " + fieldSize);
            }
            if(pos.y < 0 || pos.y >= fieldSize) {
                throw new IllegalArgumentException("y value out of bounds: y is " + pos.y + ", but should be 0 <= y < " + fieldSize);
            }

            // paint new bike position
            GraphicsContext g = this.getGraphicsContext2D();
            g.setFill(screenCommons.getColor(playerToDraw.getId())); //Color.PAPAYAWHIP);
            g.fillRect(pos.x*windowSize/fieldSize, pos.y*windowSize/fieldSize, windowSize/fieldSize, windowSize/fieldSize);
        }
    }
    private void kill(int playerToKill){}

    public void prepareTest(){
        int i=0;
        for (Player p : playerMap.values()) {
            p.setPos(i,i);
            i+=2;
        }
    }



}
