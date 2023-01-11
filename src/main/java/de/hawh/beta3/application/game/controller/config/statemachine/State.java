package de.hawh.beta3.application.game.controller.config.statemachine;


public interface State {

    void behavior();

    void setCurrentState(String state);
}
