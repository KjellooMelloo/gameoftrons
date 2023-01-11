package de.hawh.beta3.application.game.controller.config.statemachine;

public interface Waiting extends State {
    @Override
    void behavior();

    @Override
    void setCurrentState(String state);

    void handleWaitingButtonClick();
}
