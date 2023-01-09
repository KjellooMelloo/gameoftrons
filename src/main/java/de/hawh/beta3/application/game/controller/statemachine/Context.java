package de.hawh.beta3.application.game.controller.statemachine;

import de.hawh.beta3.application.game.controller.config.IConfig;
import de.hawh.beta3.application.game.model.gamemanager.IModel;
import de.hawh.beta3.application.game.view.IControllerView;

public class Context implements State {

    State currentState;
    IConfig iconfig;
    IModel imodel;
    IControllerView icontrollerview;

    @Override
    public void behavior(){

    }

    @Override
    public void setCurrentState(String state) {

    }
}
