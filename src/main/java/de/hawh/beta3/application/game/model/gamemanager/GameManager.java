package de.hawh.beta3.application.game.model.gamemanager;

import de.hawh.beta3.application.game.controller.IModelController;
import de.hawh.beta3.application.game.model.gamelogic.GameLogic;
import de.hawh.beta3.application.game.model.gamelogic.IGameLogic;
import de.hawh.beta3.application.game.view.IModelView;

public class GameManager implements IModel {
    private GameManager gameInstance = new GameManager();
    private IGameLogic gameLogic;
    private IModelController modelController;
    private IModelView modelView;

    private GameManager() {
        gameLogic = new GameLogic();
    }

    /**
     * Method returns singleton IModel instance
     *
     * @return instance of IModel
     */
    @Override
    public IModel getInstance() {
        return gameInstance;
    }

    /**
     * Method for starting the game with starting params
     *
     * @param numPlayers number of Players (2-6)
     * @param size       size of game field
     * @param gameSpeed  speed of game
     */
    //TODO Startpositionen an View schicken?
    @Override
    public void startGame(int numPlayers, int size, int gameSpeed) {
        gameLogic.init(numPlayers, size, gameSpeed);
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
                //modelView.updatePlayer(p[0], p[1], p[2]);
            }
        } else if (gameLogic.getGameState().equals("OVER")) {
            //modelController.endGame(gameLogic.getGameWinner());
        }
    }
}
