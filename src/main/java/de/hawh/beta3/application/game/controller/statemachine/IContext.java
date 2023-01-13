package de.hawh.beta3.application.game.controller.statemachine;

public interface IContext {

    State getState();
    void setCurrentState(State state);

}
