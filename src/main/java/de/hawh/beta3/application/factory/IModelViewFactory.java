package de.hawh.beta3.application.factory;

import de.hawh.beta3.application.game.view.IModelView;
import de.hawh.beta3.application.stub.caller.IModelViewCaller;

class IModelViewFactory {

    public static IModelView getModelView(String type) {
        if (type.equalsIgnoreCase("R")) {
            return new IModelViewCaller();  //IModelViewCaller.getInstance();
        } else if (type.equalsIgnoreCase("L")) {
            return null; //ModelViewImpl.getInstance();
        } else {
            return null;
        }
    }
}
