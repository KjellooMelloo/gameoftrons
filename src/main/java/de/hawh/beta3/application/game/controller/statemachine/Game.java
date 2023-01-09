package de.hawh.beta3.application.game.controller.statemachine;

public interface Game extends State {
    @Override
    void behavior();

    @Override
    void setCurrentState(String state);

    String handleDirectionKeyboardInput();
}
