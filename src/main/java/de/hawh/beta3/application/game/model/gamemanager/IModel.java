package de.hawh.beta3.application.game.model.gamemanager;

public interface IModel {

    /**
     * Methods adds player to the game and checks if game is ready or waiting timer ended
     * First call decides size of lobby
     *
     * @param playerCount number of players to play with
     * @param maxWaitingTime max waiting time until game is canceled
     */
    void join(int playerCount, int maxWaitingTime);

    /**
     * Method is called when Cancel Button is clicked or when waiting timer ended
     */
    void cancelWait();

    /**
     * Method for starting the game with starting params
     *
     * @param size          size of game field
     * @param gameSpeed     speed of game
     */
    void startGame(int size, int gameSpeed);

    /**
     * Method for changing direction of a player with <code>id</code>
     *
     * @param id        id of player
     * @param action    "left" or "right"
     */
    void changePlayerDirection(int id, String action);

    void setNumPlayers(int numPlayers);
}
