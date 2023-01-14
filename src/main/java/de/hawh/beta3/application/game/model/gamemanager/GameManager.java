package de.hawh.beta3.application.game.model.gamemanager;

import de.hawh.beta3.application.game.controller.statemachine.IModelController;
import de.hawh.beta3.application.game.controller.statemachine.IModelController;
import de.hawh.beta3.application.game.model.gamelogic.GameLogic;
import de.hawh.beta3.application.game.model.gamelogic.IGameLogic;
import de.hawh.beta3.application.game.view.IModelView;

import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class GameManager implements IModel {
    private static GameManager gameInstance = new GameManager();
    private IGameLogic gameLogic;
    private IModelController modelController;
    private IModelView modelView;
    private int fullPlayerCount;
    private int numPlayers;
    private Timer timer = new Timer();
    private Timeline timeline;

    private GameManager() {
        gameLogic = new GameLogic();
    }

    /**
     * Method returns singleton GameManager instance
     *
     * @return instance of GameManager
     */
    public static GameManager getInstance() {
        return gameInstance;
    }

    /**
     * Methods adds player to the game and checks if game is ready or waiting timer ended
     * First call decides size of lobby
     *
     * @param playerCount number of players to play with
     * @param maxWaitingTime max waiting time until game is canceled
     */
    @Override
    public void join(int playerCount, int maxWaitingTime) {
        if (fullPlayerCount != 0) {
            //IModelView.informUser("Deine Eingabe ist uns egal, wir spielen mit {fullPlayerCount} Spielern!")
        } else {
            fullPlayerCount = playerCount;
            //IModelController.setCurrentState("WAITING");
        }
        numPlayers++;

        if (numPlayers == fullPlayerCount) {
            //IModelController.setCurrentState("GAME");
        } else {
            //IModelView.updateNumPlayers(numPlayers);
            timer.cancel();
            timer = new Timer();
            TimerTask task = new WaitingTimer();
            timer.schedule(task, maxWaitingTime * 1000L);    //waiting timer to 120s
        }
    }

    /**
     * Method is called when Cancel Button is clicked or when waiting timer ended
     * It resets the lobby
     */
    @Override
    public void cancelWait() {
        fullPlayerCount = 0;
        numPlayers = 0;
        timer.cancel();
        //IModelView.informUser("Spiel wurde abgebrochen");
        //IModelController.setCurrentState("DELETE");
    }

    /**
     * Method for starting the game with starting params
     *
     * @param size       size of game field
     * @param gameSpeed  speed of game
     */
    @Override
    public void startGame(int size, int gameSpeed) {
        gameLogic.init(numPlayers, size);
        timeline = new Timeline(new KeyFrame(Duration.millis(gameSpeed), e -> update()));
        timeline.setCycleCount(Animation.INDEFINITE);
    }

    /**
     * Method for changing direction of a player with <code>id</code>
     *
     * @param id     id of player
     * @param action "left" or "right"
     */
    @Override
    public void changePlayerDirection(int id, String action) {
        gameLogic.changePlayerDirection(id, action);
    }

    /**
     * Gameloop of the game/ tick-method
     * Updates all players and sends update information to the view or controller, if game ended
     */
    private void update() {
        gameLogic.updatePlayers();
        if (gameLogic.getGameState().equals("RUNNING")) {
            for (int[] p : gameLogic.getPlayerPositions()) {
                //modelView.updatePlayer(p[0], p[1], p[2], p[3]);
            }
        } else if (gameLogic.getGameState().equals("OVER")) {
            //modelController.endGame(gameLogic.getGameWinner());
            timeline.stop();
        }
    }
}
