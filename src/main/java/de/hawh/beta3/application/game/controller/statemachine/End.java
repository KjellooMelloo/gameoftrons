package de.hawh.beta3.application.game.controller.statemachine;

import de.hawh.beta3.application.game.view.IControllerView;
import de.hawh.beta3.application.game.view.Screen.IViewImpl;

public class End implements State {

    IControllerView iView;

    public End() {
        this.behavior();
        this.iView = IViewImpl.getInstance();
    }

    @Override
    public void behavior() {

        iView.showScreen("end");
        //Timer in View
        //View ruft iModel.cancelWait() auf
    }
}
