package de.hawh.beta3.application.factory;

import de.hawh.beta3.application.game.controller.IModelController;
import de.hawh.beta3.application.stub.caller.IModelControllerCaller;

class IModelControllerFactory {

    public static IModelController getModelController(boolean remote) {
        return remote ? new IModelControllerCaller() /*IModelControllerCaller.getInstance()*/ : null; //ModelControllerImpl.getInstance();
    }
}
