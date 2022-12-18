package de.hawh.beta3.application.game.model.gamemanager;

public interface IModel {

    /**
     * Method returns singleton IModel instance
     * @return instance of IModel
     */
    IModel getInstance();

    /**
     * Method for starting the game with starting params
     *
     * @param numPlayers    number of Players (2-6)
     * @param size          size of game field
     * @param gameSpeed     speed of game
     */
    void startGame(int numPlayers, int size, int gameSpeed);

    /**
     * Method for changing direction of a player with <code>id</code>
     *
     * @param id        id of player
     * @param action    "left" or "right"
     */
    void changePlayerDirection(int id, String action);
}
