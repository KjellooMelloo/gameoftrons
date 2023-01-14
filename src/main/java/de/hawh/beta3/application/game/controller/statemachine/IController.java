package de.hawh.beta3.application.game.controller.statemachine;

public interface IController extends IContext, IModelController, IViewController {

    State getState();
    void setCurrentState(String state);

    void handleInputPlayerCount(int playerCount);
    void handleWaitingButtonClick();
    void handleDirectionKeyboardInput();
    void notifyCountdownOver();

    void endGame(int result);
}
