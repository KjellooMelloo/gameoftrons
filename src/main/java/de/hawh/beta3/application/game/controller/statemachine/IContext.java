package de.hawh.beta3.application.game.controller.statemachine;

public interface IContext {

    void setCurrentState(String state);

    void handleInputPlayerCount(int playerCount);
    void handleWaitingButtonClick();
    void handleDirectionKeyboardInput(String key);
    //void handleDirectionKeyboardInput(int id, String key);
    void notifyCountdownOver();

    void endGame(int result);

}
