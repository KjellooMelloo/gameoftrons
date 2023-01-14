package de.hawh.beta3.application.game.controller.statemachine;

public interface IController extends IContext, IModelController, IViewController {

    State getState();
    void setCurrentState(State state);

    void handleInputPlayerCount(int playerCount);
    void handleWaitingButtonClick();
    String handleDirectionKeyboardInput();
    void notifyCountdownOver();

    void endGame(int result);
}
