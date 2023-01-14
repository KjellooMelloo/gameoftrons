package de.hawh.beta3.application.factory;

import de.hawh.beta3.application.game.view.IModelView;
import de.hawh.beta3.application.game.view.Screen.IModelViewImpl;
import de.hawh.beta3.application.stub.caller.IModelViewCaller;

class IModelViewFactory {

    public static IModelView getModelView(boolean remote) {
        return remote ? new IModelViewCaller()  /*IModelViewCaller.getInstance()*/ : IModelViewImpl.getInstance(); //;
    }
}
