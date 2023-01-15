package de.hawh.beta3.application.game.controller.statemachine;

import de.hawh.beta3.application.game.view.IControllerView;
import de.hawh.beta3.application.game.view.Screen.IViewImpl;

public class Start implements State {

    IControllerView iView;
    Context context;

    public Start(){
        this.behavior();
        this.context = Context.getInstance();
        this.iView = IViewImpl.getInstance();
    }

    @Override
    public void behavior(){

        iView.showScreen("start");
    }

}
