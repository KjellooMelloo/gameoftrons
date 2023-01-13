package de.hawh.beta3.application.game.controller.statemachine;

import de.hawh.beta3.application.game.model.gamemanager.IModel;
import de.hawh.beta3.application.game.view.IControllerView;

public class End implements State {

    IControllerView iView;
    IModel iModel;
    Context context;

    public End(Context context) {
        this.behavior(context);
    }

    @Override
    public void behavior(Context context) {
        //to do
        iView.showScreen("End");
        //Timer extern oder hier
        context.setCurrentState(new Delete(context));
    }
}
