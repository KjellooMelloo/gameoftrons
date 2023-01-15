package de.hawh.beta3.application.game.controller.statemachine;

import de.hawh.beta3.application.game.view.IControllerView;

public class Start implements State {

    IControllerView iView;
    Context context;

    public Start(Context context){
        this.behavior(context);
        this.context = context;
    }

    @Override
    public void behavior(Context context){

        iView.showScreen("start");
    }

}
