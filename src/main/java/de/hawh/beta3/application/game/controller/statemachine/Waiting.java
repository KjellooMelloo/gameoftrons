package de.hawh.beta3.application.game.controller.statemachine;

import de.hawh.beta3.application.game.model.gamemanager.IModel;
import de.hawh.beta3.application.game.view.IControllerView;

public class Waiting implements State {

    IControllerView iView;
    IModel iModel;
    Context context;

    public Waiting(Context context){
        this.behavior(context);
        this.context = context;
    }

    @Override
    public void behavior(Context context){
        iView.showScreen("Waiting");
    }

    public void handleWaitingButtonClick(){
        iModel.cancelWait(); //LOKAL
        context.setCurrentState(new Delete(context));
    }
}
