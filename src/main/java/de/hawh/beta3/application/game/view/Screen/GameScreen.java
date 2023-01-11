package de.hawh.beta3.application.game.view.Screen;

import de.hawh.beta3.application.game.view.Player.Coordinate;
import de.hawh.beta3.application.game.view.Player.Player;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.transform.Rotate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GameScreen extends Canvas {

    private int currentPlayerID=-1;
    private int fieldSize=40;

    private Map<Integer, Player> playerMap = new HashMap<>();
    private int windowSize = 800;
    private int initialNumPlayers = 2;
    private ScreenCommons screenCommons = new ScreenCommons();
    private Color backgroundColor = Color.BLUEVIOLET.darker().darker().darker().desaturate();



    public GameScreen(int currentPlayerID, int initialNumPlayers, int fieldSize){
        this.currentPlayerID = currentPlayerID;
        this.initialNumPlayers = initialNumPlayers;
        this.fieldSize = 10;


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

        g.clearRect(0, 0, getWidth(), getHeight());

        // vertical lines
        g.setStroke(Color.GRAY);
        for(int i = 0 ; i <= getWidth() ; i+=windowSize/fieldSize){
            g.strokeLine(i, 0, i, getHeight() - (getHeight()%30));
        }

        // horizontal lines
        g.setStroke(Color.GRAY);
        for(int i = windowSize/fieldSize ; i < getHeight() ; i+=windowSize/fieldSize){
            g.strokeLine(0, i, getWidth(), i);
        }
        //TODO erase after test
        prepareTest();


        for(Player p:playerMap.values()){
            drawTileColors(p);
        }



        updatePlayer(0,1,2,"DOWN");



    }

    public GameScreen(){}


    private void updatePlayer(int playerID,int newX, int newY, String newOrientation){
        if(newX==-1 || newY==-1){
            kill(playerID);
            return;
        }
        Player playerToUpdate = playerMap.get(playerID);
        this.getGraphicsContext2D().clearRect(playerToUpdate.getPos().x*windowSize/fieldSize, playerToUpdate.getPos().y*windowSize/fieldSize,
                windowSize/fieldSize, windowSize/fieldSize);
        playerToUpdate.updateTrailAndOrientation(newX,newY,newOrientation);
        drawTileColors(playerToUpdate);
    }
    private void removeTileColors(int playerID){}
    private void drawTileColors(Player playerToDraw){
        if(playerToDraw == null || playerToDraw.getTrail()==null){
            throw new NullPointerException();
        }
        GraphicsContext g = this.getGraphicsContext2D();

        for(Coordinate pos : playerToDraw.getTrail()){
            if(pos.x < 0 || pos.x >= fieldSize){
                throw new IllegalArgumentException("x value out of bounds: x is " + pos.x + ", but should be 0 <= x < " + fieldSize);
            }
            if(pos.y < 0 || pos.y >= fieldSize) {
                throw new IllegalArgumentException("y value out of bounds: y is " + pos.y + ", but should be 0 <= y < " + fieldSize);
            }

            // paint new bike position
            g.setFill(screenCommons.getColor(playerToDraw.getId())); //Color.PAPAYAWHIP);
            g.fillRect(pos.x*windowSize/fieldSize, pos.y*windowSize/fieldSize, windowSize/fieldSize, windowSize/fieldSize);
        }


        g.drawImage(playerToDraw.getImage(), playerToDraw.getPos().x*windowSize/fieldSize, playerToDraw.getPos().y*windowSize/fieldSize, windowSize/fieldSize,windowSize/fieldSize);
    }

    private void kill(int playerToKill){
        playerMap.remove(playerToKill);
        removeTileColors(playerToKill);
    }

    public void prepareTest(){
        int i = 0;
        for(Player p:playerMap.values()){
            p.setPos(i,i);
            p.getTrail().add(p.getPos());
            i+=2;
        }

    }



}
