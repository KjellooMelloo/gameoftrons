package de.hawh.beta3.application.game.controller.statemachine;

public interface IViewController extends State {

    void handleInputPlayerCount(int playerCount);
    void handleWaitingButtonClick();
    String handleDirectionKeyboardInput();
    void notifyCountdownOver();

}