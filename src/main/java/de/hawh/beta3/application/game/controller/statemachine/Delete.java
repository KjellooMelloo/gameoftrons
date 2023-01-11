package de.hawh.beta3.application.game.controller.statemachine;

public class Delete implements State {

    public Delete(Context context){
        this.behavior(context);
    }

    @Override
    public void behavior(Context context){
        //to do
        context.setCurrentState(new Start(context));
    }

    /**@Override
    public void setCurrentState(String state){

    }**/

    void deleteGameInstance(){

    }
}
