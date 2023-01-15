package de.hawh.beta3.application.game.controller.statemachine;

import java.io.IOException;

public interface IContext {

    void setCurrentState(String state);

    void handleInputPlayerCount(int playerCount) throws IOException;
    void handleWaitingButtonClick();
    void handleDirectionKeyboardInput(String key);
    void handleOfflineButton() throws IOException;
    void notifyCountdownOver();

    void endGame(int result);

}
