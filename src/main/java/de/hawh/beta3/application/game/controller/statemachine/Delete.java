package de.hawh.beta3.application.game.controller.statemachine;

public interface Delete extends State {
    @Override
    void behavior();

    @Override
    void setCurrentState(String state);

    void deleteGameInstance();
}
