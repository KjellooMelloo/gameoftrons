package de.hawh.beta3.application.game.view.Screen;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.VBox;

public class GameScreen extends VBox {

    private int currentPlayerID=-1;
    private int fieldSize;
    private int[][] gameField;
    private Canvas canvas;


    public GameScreen(SimpleIntegerProperty currentPlayerIDArg){
        this.currentPlayerID = currentPlayerIDArg.get();
        currentPlayerIDArg.addListener(e->{
            this.currentPlayerID = currentPlayerIDArg.get();
        });
    }

    private void drawPlayers(){}
    private void updatePlayer(int playerID,int newX, int newY, int newOrientation){}
    private void removeTileColors(int playerID){}
    private void drawTileColors(int playerID, int oldx, int oldY, int difX, int difY, String oldOrientation){}
    private void kill(int playerToKill){}



}
