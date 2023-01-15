package de.hawh.beta3.application.game.factory;

import de.hawh.beta3.application.game.model.gamemanager.GameManager;
import de.hawh.beta3.application.game.model.gamemanager.IModel;
import de.hawh.beta3.application.stub.caller.IModelCaller;

class IModelFactory {

    public static IModel getModel(boolean remote) {
        return remote ? new IModelCaller() : GameManager.getInstance();
    }
}
