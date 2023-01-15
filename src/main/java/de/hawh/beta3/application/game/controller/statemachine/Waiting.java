package de.hawh.beta3.application.game.controller.statemachine;

import de.hawh.beta3.application.game.view.IControllerView;
import de.hawh.beta3.application.game.view.Screen.IViewImpl;

public class Waiting implements State {

    IControllerView iView;
    Context context;

    public Waiting(){
        this.behavior();
        this.iView = IViewImpl.getInstance();
        this.context = Context.getInstance();
    }

    @Override
    public void behavior(){
        iView.showScreen("lobby");
    }

}
