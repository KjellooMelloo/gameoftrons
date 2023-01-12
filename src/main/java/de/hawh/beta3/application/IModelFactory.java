package de.hawh.beta3.application;

import de.hawh.beta3.application.game.model.gamemanager.GameManager;
import de.hawh.beta3.application.game.model.gamemanager.IModel;
import de.hawh.beta3.application.stub.caller.IModelCaller;

public class IModelFactory {

    public static IModel getModel(String type) {
        if (type.equalsIgnoreCase("R")) {
            return new IModelCaller();  //IModelCaller.getInstance();
        } else if (type.equalsIgnoreCase("L")) {
            return GameManager.getInstance();
        } else {
            return null;
        }
    }
}
