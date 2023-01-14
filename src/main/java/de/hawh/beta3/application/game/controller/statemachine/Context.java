package de.hawh.beta3.application.game.controller.statemachine;

public class Context implements IContext {

    Context context;
    int[] configParameters;

    private State currentState;

    public Context(){
        context = this;
        currentState = new Start(context);
    }
    private State getState(){
        return currentState;
    }

    public void setCurrentState(String state){
        this.currentState = getStateFromString(state);
    }

    public State getStateFromString(String state){
        switch (state){
            case "START":
                return new Start(context);
            case "WAITING":
                return new Waiting(context);
            case "GAME":
                return new Game(context);
            case "END":
                return new End(context);
            case "DELETE":
                return new Delete(context);
            default:
                return new Start(context);
        }
    }
}
