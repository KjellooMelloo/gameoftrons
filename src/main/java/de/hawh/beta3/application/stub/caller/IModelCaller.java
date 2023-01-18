package de.hawh.beta3.application.stub.caller;

import de.hawh.beta3.application.game.model.gamemanager.IModel;
import de.hawh.beta3.middleware.IMiddleware;
import de.hawh.beta3.middleware.Middleware;

import java.util.UUID;

public class IModelCaller implements IModel {

    private IMiddleware mw = Middleware.getInstance();
    private int id = 0;
    private UUID uuid = UUID.fromString("c17b85b2-48ec-44e4-8fac-9fd7760dcbd3");


    /**
     * Methods adds player to the game and checks if game is ready or waiting timer ended
     * First call decides size of lobby
     *
     * @param playerCount number of players to play with
     * @param maxWaitingTime max waiting time until game is canceled
     */
    @Override
    /**public void join(int playerCount, int maxWaitingTime) {
        mw.invoke(id, "join", new Object[]{playerCount, maxWaitingTime});
    }**/
    public void join(int playerCount, int maxWaitingTime) {
        mw.invoke(uuid, "join", new Object[]{playerCount, maxWaitingTime});
    }

    /**
     * Method is called when Cancel Button is clicked or when waiting timer ended
     */
    @Override
    /**public void cancelWait() {
        mw.invoke(id, "cancelWait", new Object[]{});
    }**/
    public void cancelWait() {
        mw.invoke(uuid, "cancelWait", new Object[]{});
    }

    /**
     * Method for starting the game with starting params
     *
     * @param size      size of game field
     * @param gameSpeed speed of game
     */
    @Override
    /**public void startGame(int size, int gameSpeed) {
        mw.invoke(id, "startGame", new Object[]{size, gameSpeed});
    }**/
    public void startGame(int size, int gameSpeed) {
        mw.invoke(uuid, "startGame", new Object[]{size, gameSpeed});
    }

    /**
     * Method for changing direction of a player with <code>id</code>
     *
     * @param id     id of player
     * @param action "left" or "right"
     */
    @Override
    /**public void changePlayerDirection(int id, String action) {
        mw.invoke(this.id, "changePlayerDirection", new Object[]{id, action});
    }**/
    public void changePlayerDirection(int id, String action) {
        mw.invoke(this.uuid, "changePlayerDirection", new Object[]{id, action});
    }

    @Override
    /**public void setNumPlayers(int numPlayers){
        mw.invoke(id,"setNumPlayers", new Object[]{numPlayers});
    }**/
    public void setNumPlayers(int numPlayers){
        mw.invoke(uuid,"setNumPlayers", new Object[]{numPlayers});
    }

}
