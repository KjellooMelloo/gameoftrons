package de.hawh.beta3.application.game.controller.config.statemachine;

public interface Start extends State {

    @Override
    void behavior();

    @Override
    void setCurrentState(String state);

    int handleInputPlayerCount();
}
