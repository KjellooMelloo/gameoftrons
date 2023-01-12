package de.hawh.beta3.application.stub.caller;

import de.hawh.beta3.application.game.controller.IModelController;
import de.hawh.beta3.middleware.IMiddleware;

public class IModelControllerCaller implements IModelController {

    private IMiddleware mw; //= new Middleware();
    private int id = 2;

    //@Override
    public void setCurrentState(String State){
        mw.invoke(id, "setCurrentState", new Object[]{State});
    }

    //@Override
    public void endGame(int result){
        mw.invoke(id, "endGame", new Object[]{result});
    }
}
