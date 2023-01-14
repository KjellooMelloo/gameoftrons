package de.hawh.beta3.application.game.controller.statemachine;

public class Context implements IContext {

    Context context;
    int[] configParameters;

    private State currentState;

    public Context(){
        context = this;
        currentState = new Start(context);
    }
    public State getState(){
        return currentState;
    }

    public void setCurrentState(State state){
        this.currentState = state;
    }

}