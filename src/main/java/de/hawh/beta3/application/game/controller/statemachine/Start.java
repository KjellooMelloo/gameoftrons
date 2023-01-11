package de.hawh.beta3.application.game.controller.statemachine;

public class Start implements State {

    public Start(Context context){
        this.behavior(context);
    }

    @Override
    public void behavior(Context context){
        //to do
        context.setCurrentState(new Waiting(context));
    }

    /**@Override
    public void setCurrentState(String state){

    }**/

    public int handleInputPlayerCount(){
        return 0;
    }
}
