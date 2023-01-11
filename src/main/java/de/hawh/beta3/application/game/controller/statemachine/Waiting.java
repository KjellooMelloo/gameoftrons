package de.hawh.beta3.application.game.controller.statemachine;

public class Waiting implements State {

    public Waiting(Context context){
        this.behavior(context);
    }

    @Override
    public void behavior(Context context){
        //to do
        //showScreen("Waiting");
        //if (handleWaitingButtonClick){
        //      iModel.cancelWait(); LOKAL
        //      context.setCurrentState(new Delete(context));
        //}
        //else if (notifyTimerstopped()){
        //      iModel.cancelWait() REMOTE BEI ALLEN
        //else if (fullPlayerCountReached()){
        //      context.setCurrentState(new Game(context));
        //}
        //else {
        //      WAIT
        //}
    }

    public void handleWaitingButtonClick(){}
}
