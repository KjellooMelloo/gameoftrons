package de.hawh.beta3.application.game.controller.statemachine;

import de.hawh.beta3.application.game.view.IControllerView;
import de.hawh.beta3.application.game.view.Screen.IViewImpl;

public class End implements State {

    IControllerView iView;

    public End() {
        this.iView = IViewImpl.getInstance();
        this.behavior();
    }

    @Override
    public void behavior() {

        iView.showScreen("end");
        //Timer in View
        //View ruft iModel.cancelWait() auf
    }
}
