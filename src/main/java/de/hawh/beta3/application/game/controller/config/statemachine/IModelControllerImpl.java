package de.hawh.beta3.application.game.controller.config.statemachine;

public class IModelControllerImpl extends Context implements IModelController, End {

    State currentState;
    Context context;



    @Override
    public void behavior(){
        if (context.currentState == end){
            end.behavior();
        }
    }

    @Override
    public void setCurrentState(String State){

    }

    @Override
    public void endGame(int result){

    }

}
