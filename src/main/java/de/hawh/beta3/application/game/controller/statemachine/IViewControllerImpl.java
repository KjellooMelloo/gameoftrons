package de.hawh.beta3.application.game.controller.statemachine;

import de.hawh.beta3.application.game.controller.config.Config;

public class IViewControllerImpl extends Context implements IViewController {

    State currentState;
    Config config;

    /**@Override
    public void behavior(){

    }**/

    /**@Override
    public void setCurrentState(String state){

    }**/

    @Override
    public int handleInputPlayerCount() {
        return 0;
    }

    @Override
    public void handleWaitingButtonClick() {

    }

    @Override
    public String handleDirectionKeyboardInput() {
        return null;
    }

    @Override
    public void notifyCountdownOver() {

    }
}
