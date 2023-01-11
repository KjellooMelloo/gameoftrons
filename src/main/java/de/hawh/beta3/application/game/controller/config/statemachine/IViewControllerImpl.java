package de.hawh.beta3.application.game.controller.config.statemachine;

import de.hawh.beta3.application.game.controller.config.Config;

public class IViewControllerImpl extends Context implements IViewController, Start, Waiting, Game {

    State currentState;
    Config config;

    @Override
    public void behavior(){

    }

    @Override
    public void setCurrentState(String State){

    }

    @Override
    public int handleInputPlayerCount(){
        return 0;
    }

    @Override
    public void handleWaitingButtonClick(){

    }

    @Override
    public String handleDirectionKeyboardInput(){
        return "";
    }

    @Override
    public void notifyCountdownOver(){

    }
}
