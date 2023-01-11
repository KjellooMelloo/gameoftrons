package de.hawh.beta3.application.game.controller.statemachine;

public class IModelControllerImpl extends Context implements IModelController {

    State currentState;
    Context context;


    /**@Override
    public void behavior(){
        if (context.currentState == end){
            end.behavior();
        }
    }**/

    /**@Override
    public void setCurrentState(String state){

    }**/

    @Override
    public void endGame(int result){

    }

}
