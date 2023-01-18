package de.hawh.beta3.application.stub.caller;

import de.hawh.beta3.application.game.controller.statemachine.Context;
import de.hawh.beta3.application.game.controller.statemachine.IContext;
import de.hawh.beta3.middleware.IMiddleware;
import de.hawh.beta3.middleware.Middleware;

import java.util.UUID;

public class IControllerCaller implements IContext {

    private IMiddleware mw = Middleware.getInstance();
    private int id = 2;
    private UUID uuid = UUID.fromString("dc9b47f5-a7cc-456c-9cc8-78c7e457f1c2");


    @Override
    /**public void setCurrentState(String State){
            mw.invoke(id, "setCurrentState", new Object[]{State});
        }**/
    public void setCurrentState(String State){
        mw.invoke(uuid, "setCurrentState", new Object[]{State});
    }


    @Override
    /**public void handleInputPlayerCount(int playerCount) {
        mw.invoke(id,"handleInputPlayerCount", new Object[]{playerCount});
    }**/

    public void handleInputPlayerCount(int playerCount) {
        mw.invoke(uuid,"handleInputPlayerCount", new Object[]{playerCount});
    }


    @Override
    /**public void handleWaitingButtonClick() {
        mw.invoke(id,"handleWaitingButtonClick", new Object[]{});
    }**/

    public void handleWaitingButtonClick() {
     mw.invoke(uuid,"handleWaitingButtonClick", new Object[]{});
     }

    @Override
    /**public void handleOfflineButton() {mw.invoke(id, "handleOfflineButton", new Object[]{});}**/
    public void handleOfflineButton() {mw.invoke(uuid, "handleOfflineButton", new Object[]{});}


    @Override
    /**public void handleDirectionKeyboardInput(String key) {
        mw.invoke(id,"handleDirectionKeyboardInput", new Object[]{key});
    }**/
    public void handleDirectionKeyboardInput(String key) {
        mw.invoke(uuid,"handleDirectionKeyboardInput", new Object[]{key});
    }

    /**@Override
    public void handleDirectionKeyboardInput(int id, String key) {
        mw.invoke(id,"handleDirectionKeyboardInput", new Object[]{id, key});
    }**/

    @Override
    /**public void notifyCountdownOver() {
        mw.invoke(id,"notifyCountdownOver", new Object[]{});
    }**/
    public void notifyCountdownOver() {
        mw.invoke(uuid,"notifyCountdownOver", new Object[]{});
    }

    @Override
    /**public void endGame(int result){
            mw.invoke(id, "endGame", new Object[]{result});
        }**/

    public void endGame(int result){
        mw.invoke(uuid, "endGame", new Object[]{result});
    }

}

