package de.hawh.beta3.application;

import de.hawh.beta3.application.game.controller.IModelController;
import de.hawh.beta3.application.stub.caller.IModelControllerCaller;

public class IModelControllerFactory {

    public static IModelController getModelController(String type) {
        if (type.equalsIgnoreCase("R")) {
            return new IModelControllerCaller();  //IModelControllerCaller.getInstance();
        } else if (type.equalsIgnoreCase("L")) {
            return null; //ModelControllerImpl.getInstance();
        } else {
            return null;
        }
    }
}
