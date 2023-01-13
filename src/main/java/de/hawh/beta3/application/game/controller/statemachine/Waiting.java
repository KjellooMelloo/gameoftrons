package de.hawh.beta3.application.game.controller.statemachine;

import de.hawh.beta3.application.game.model.gamemanager.IModel;
import de.hawh.beta3.application.game.view.IControllerView;

public class Waiting implements State {

    IControllerView iView;
    IModel iModel;

    public Waiting(Context context){
        this.behavior(context);
    }

    @Override
    public void behavior(Context context){
        //to do
        iView.showScreen("Waiting");
        //if (handleWaitingButtonClick){
              iModel.cancelWait(); //LOKAL
              context.setCurrentState(new Delete(context));
        //}
        //else if (notifyTimerstopped()){
              iModel.cancelWait(); //REMOTE BEI ALLEN
        //else if (fullPlayerCountReached()){
              context.setCurrentState(new Game(context));
        //}
        //else {
        //      WAIT
        //}
    }

    public void handleWaitingButtonClick(){

    }
}
