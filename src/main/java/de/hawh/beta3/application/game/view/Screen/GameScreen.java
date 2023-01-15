package de.hawh.beta3.application.game.view.Screen;


import de.hawh.beta3.application.game.view.Player.Coordinate;
import de.hawh.beta3.application.game.view.Player.Player;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Glow;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class GameScreen extends Canvas {

    private final Map<Integer, Player> playerMap = new HashMap<>();
    private final int windowSize = 800;
    private int currentPlayerID = -1;
    private int fieldSize = 10;


    public GameScreen() {

        // Set up canvas size
        this.setWidth(windowSize);
        this.setHeight(windowSize);



        // Register Key Event Handler
        registerKeyEventHandler();


    }


    private void registerKeyEventHandler() {
        this.setFocusTraversable(true);
        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String keyCode = keyEvent.getCode().toString();
                ScreenCommons.CONTROLLER.handleDirectionKeyboardInput(keyCode);
            }
        });
    }


    public void initializeGameField(int numPlayers) {
        System.out.println("Initializing game field");
        initPlayersInPositions(numPlayers);
        GraphicsContext g = this.getGraphicsContext2D();


        // vertical lines
        g.setStroke(Color.GRAY);
        for (int i = 0; i <= getWidth(); i += windowSize / fieldSize) {
            g.strokeLine(i, 0, i, getHeight() - (getHeight() % windowSize / fieldSize));
        }


        // horizontal lines
        for (int i = 0; i <= getHeight(); i += windowSize / fieldSize) {
            g.strokeLine(0, i, getWidth(), i);
        }

        for(Player p:playerMap.values()){
            drawTileColors(p);
        }

    }

    private void initPlayersInPositions(int numPlayers) {

        System.out.println("initializing player");
        System.out.println("NumPlayers: " + numPlayers);

        Coordinate[] startingPos;

        if (numPlayers < 5) {
            startingPos = new Coordinate[]{
                    new Coordinate(0, (fieldSize - 1) / 2),
                    new Coordinate((fieldSize - 1), (fieldSize - 1) / 2),
                    new Coordinate((fieldSize - 1) / 2, 0),
                    new Coordinate((fieldSize - 1) / 2, (fieldSize - 1))
            };
        } else {
            startingPos = new Coordinate[]{
                    new Coordinate(0, (fieldSize - 1) / 3),
                    new Coordinate((fieldSize - 1), (fieldSize - 1) / 3),
                    new Coordinate((fieldSize - 1) / 2, 0),
                    new Coordinate((fieldSize - 1) / 2, (fieldSize - 1)),
                    new Coordinate(0, 2 * (fieldSize - 1) / 3),
                    new Coordinate((fieldSize - 1), 2 * (fieldSize - 1) / 3)
            };
        }

        Coordinate[] positionsToInitialize = Arrays.copyOf(startingPos, numPlayers);
        String[] startingDir = new String[]{
                "RIGHT",
                "LEFT",
                "DOWN",
                "UP",
                "RIGHT",
                "LEFT"
        };

        for (int i = 0; i < numPlayers; i++) {
            playerMap.put(i, new Player(i, positionsToInitialize[i], startingDir[i]));
        }
    }


    public void updatePlayer(int playerID, int newX, int newY, String newOrientation) {
        if (newX == -1 || newY == -1) {
            kill(playerID);
            return;
        }
        Player playerToUpdate = playerMap.get(playerID);
        this.getGraphicsContext2D().clearRect(playerToUpdate.getPos().x * windowSize / fieldSize, playerToUpdate.getPos().y * windowSize / fieldSize,
                windowSize / fieldSize, windowSize / fieldSize);
        playerToUpdate.updateTrailAndOrientation(newX, newY, newOrientation);
        drawTileColors(playerToUpdate);
    }

    private void removeTileColors(Player playerToRemove) {

        GraphicsContext g = this.getGraphicsContext2D();

        for (Coordinate pos : playerToRemove.getTrail()) {
            if (pos.x < 0 || pos.x >= fieldSize) {
                throw new IllegalArgumentException("x value out of bounds: x is " + pos.x + ", but should be 0 <= x < " + fieldSize);
            }
            if (pos.y < 0 || pos.y >= fieldSize) {
                throw new IllegalArgumentException("y value out of bounds: y is " + pos.y + ", but should be 0 <= y < " + fieldSize);
            }

            // erase trail position
            g.clearRect(pos.x * windowSize / fieldSize, pos.y * windowSize / fieldSize, windowSize / fieldSize, windowSize / fieldSize);
        }
    }

    private void drawTileColors(Player playerToDraw) {
        if (playerToDraw == null || playerToDraw.getTrail() == null) {
            throw new NullPointerException();
        }
        GraphicsContext g = this.getGraphicsContext2D();


        for (Coordinate pos : playerToDraw.getTrail()) {
            if (pos.x < 0 || pos.x >= fieldSize) {
                throw new IllegalArgumentException("x value out of bounds: x is " + pos.x + ", but should be 0 <= x < " + fieldSize);
            }
            if (pos.y < 0 || pos.y >= fieldSize) {
                throw new IllegalArgumentException("y value out of bounds: y is " + pos.y + ", but should be 0 <= y < " + fieldSize);
            }

            // paint new bike position
            g.setFill(ScreenCommons.getColor(playerToDraw.getId()));
            g.fillRect(pos.x * windowSize / fieldSize, pos.y * windowSize / fieldSize, windowSize / fieldSize, windowSize / fieldSize);
        }

        ColorAdjust bikeColor = new ColorAdjust();
        Glow glow = new Glow(1.0);


        g.save();
        if (playerToDraw.getId() == currentPlayerID) {
            g.setEffect(glow);
        }
        g.drawImage(playerToDraw.getImage(), playerToDraw.getPos().x * windowSize / fieldSize, playerToDraw.getPos().y * windowSize / fieldSize, windowSize / fieldSize, windowSize / fieldSize);
        g.restore();
    }

    private void kill(int playerToKillID) {
        Player playerToRemove = playerMap.get(playerToKillID);
        playerMap.remove(playerToKillID);
        removeTileColors(playerToRemove);
    }

   /* public void prepareTest(){
        currentPlayerID=1;
        int i = 0;
        for(Player p:playerMap.values()){
            p.setPos(i,i);
            p.getTrail().add(p.getPos());
            i+=2;
        }

    }*/

    public void setFieldSize(int fieldSize) {
        this.fieldSize = fieldSize;
    }

    public void setCurrentPlayerID(int currentPlayerID) {
        this.currentPlayerID = currentPlayerID;
    }

}
