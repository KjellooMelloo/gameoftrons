package de.hawh.beta3.application.stub.caller;

import de.hawh.beta3.application.game.controller.statemachine.Context;
import de.hawh.beta3.application.game.controller.statemachine.IContext;
import de.hawh.beta3.application.game.controller.statemachine.State;
import de.hawh.beta3.middleware.IMiddleware;

public class IControllerCaller implements IContext {

    private IMiddleware mw; //= new Middleware();
    private int id = 2;

    @Override
    public void setCurrentState(String State){
            mw.invoke(id, "setCurrentState", new Object[]{State});
        }


    @Override
    public void handleInputPlayerCount(int playerCount) {
        mw.invoke(id,"handleInputPlayerCount", new Object[]{playerCount});
    }

    @Override
    public void handleWaitingButtonClick() {
        mw.invoke(id,"handleWaitingButtonClick", new Object[]{});
    }

    @Override
    public void handleDirectionKeyboardInput(String key) {
        mw.invoke(id,"handleDirectionKeyboardInput", new Object[]{key});
    }
    @Override
    public void handleDirectionKeyboardInput(int id, String key) {
        mw.invoke(id,"handleDirectionKeyboardInput", new Object[]{id, key});
    }

    @Override
    public void notifyCountdownOver() {
        mw.invoke(id,"notifyCountdownOver", new Object[]{});
    }

    @Override
    public void endGame(int result){
            mw.invoke(id, "endGame", new Object[]{result});
        }

}

