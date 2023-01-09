package de.hawh.beta3.application.game.controller.statemachine;

public interface IViewController {

    int handleInputPlayerCount();
    void handleWaitingButtonClick();
    String handleDirectionKeyboardInput();
    void notifyCountdownOver();
}
