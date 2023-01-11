package de.hawh.beta3.application.game.controller.statemachine;

public class End implements State {


    public End(Context context){
        this.behavior(context);
    }

    @Override
    public void behavior(Context context){
        //to do
        context.setCurrentState(new Delete(context));
    }

    public void endGame(int result){

    }
}
