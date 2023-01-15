package de.hawh.beta3.application.game.controller.statemachine;

import de.hawh.beta3.application.game.view.IControllerView;
import de.hawh.beta3.application.game.view.Screen.IViewImpl;

public class Game implements State {

    IControllerView iView;

    public Game(){
        this.iView = IViewImpl.getInstance();
        this.behavior();
    }

    @Override
    public void behavior(){

        iView.showScreen("countdown");

    }

}
