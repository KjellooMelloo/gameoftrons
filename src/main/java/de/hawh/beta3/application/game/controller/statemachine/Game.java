package de.hawh.beta3.application.game.controller.statemachine;

public class Game implements State {

    public Game(Context context){
        this.behavior(context);
    }

    @Override
    public void behavior(Context context){
        //to do
        context.setCurrentState(new End(context));
    }

    public String handleDirectionKeyboardInput(){
        return "";
    }
}
