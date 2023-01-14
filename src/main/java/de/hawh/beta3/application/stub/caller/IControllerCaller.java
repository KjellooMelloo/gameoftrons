package de.hawh.beta3.application.stub.caller;

import de.hawh.beta3.application.game.controller.statemachine.Context;
import de.hawh.beta3.application.game.controller.statemachine.IController;
import de.hawh.beta3.application.game.controller.statemachine.State;
import de.hawh.beta3.middleware.IMiddleware;

public class IControllerCaller implements IController {

    private IMiddleware mw; //= new Middleware();
    private int id = 2;

    @Override
    public State getState() {
        return null;
    }

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
    public void handleDirectionKeyboardInput() {
        mw.invoke(id,"handleDirectionKeyboardInput", new Object[]{});
    }

    @Override
    public void notifyCountdownOver() {
        mw.invoke(id,"notifyCountdownOver", new Object[]{});
    }

    @Override
    public void endGame(int result){
            mw.invoke(id, "endGame", new Object[]{result});
        }

    @Override
    public void behavior(Context context) {
        mw.invoke(id, "behavior", new Object[]{});
    }
}

