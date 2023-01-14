package de.hawh.beta3.application.game.controller.statemachine;

import de.hawh.beta3.application.game.view.IControllerView;

public class End implements State {

    IControllerView iView;

    public End(Context context) {
        this.behavior(context);
    }

    @Override
    public void behavior(Context context) {

        iView.showScreen("End");
        //Timer in View
        //View ruft iModel.cancelWait() auf
    }
}
