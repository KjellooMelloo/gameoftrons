package de.hawh.beta3.application.game.controller.statemachine;

public class Waiting implements State {

    public Waiting(Context context){
        this.behavior(context);
    }

    @Override
    public void behavior(Context context){
        //to do
        context.setCurrentState(new Game(context));
        context.setCurrentState(new Delete(context));
    }

    public void handleWaitingButtonClick(){}
}
