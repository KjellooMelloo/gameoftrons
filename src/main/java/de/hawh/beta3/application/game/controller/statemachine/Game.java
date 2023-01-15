package de.hawh.beta3.application.game.controller.statemachine;

import de.hawh.beta3.application.game.view.IControllerView;

public class Game implements State {

    IControllerView iView;

    public Game(Context context){
        this.behavior(context);
    }

    @Override
    public void behavior(Context context){

        iView.showScreen("countdown");

    }

}
