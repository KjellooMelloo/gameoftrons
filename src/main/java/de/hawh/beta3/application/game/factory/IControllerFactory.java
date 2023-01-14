package de.hawh.beta3.application.game.factory;

import de.hawh.beta3.application.game.controller.statemachine.IContext;
import de.hawh.beta3.application.stub.caller.IControllerCaller;

class IControllerFactory {

    public static IContext getController(boolean remote) {
        return remote ? new IControllerCaller() /*IModelControllerCaller.getInstance()*/ : null; //ModelControllerImpl.getInstance();
    }
}
