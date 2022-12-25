package de.hawh.beta3.application.game.model.gamelogic;

import java.util.List;

public interface IGameLogic {

    /**
     * Initializes game logic with params
     *
     * @param numPlayers number of Players (2-6)
     * @param size       size of game field
     * @param gameSpeed  speed of game
     */
    void init(int numPlayers, int size, int gameSpeed);

    /**
     * Method for changing direction of a player with <code>id</code>
     *
     * @param id     id of player
     * @param action "left" or "right"
     */
    void changePlayerDirection(int id, String action);

    /**
     * Updates the position of every player
     */
    void updatePlayers();

    /**
     * Return current game state <code>INIT, RUNNING, OVER</code>
     *
     * @return game state enum
     */
    String getGameState();

    /**
     * Returns the id of the winner or -1 if tied
     *
     * @return id or -1 (tie)
     */
    int getGameWinner();

    /**
     * Collects every current position (front) of players.
     * Each entry in array consists of <code>{playerID, playerXPos, playerYPos}</code>
     *
     * @return int[][] with player IDs + current x and y
     */
    int[][] getPlayerPositions();
}
