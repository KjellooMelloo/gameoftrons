package de.hawh.beta3.application.game.controller.config.statemachine;

public interface End extends State {
    @Override
    void behavior();

    @Override
    void setCurrentState(String state);

    void endGame(int result);
}
