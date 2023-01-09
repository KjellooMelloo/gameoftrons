package de.hawh.beta3.application.game.controller.statemachine;

import de.hawh.beta3.application.game.controller.config.IConfig;
import de.hawh.beta3.application.game.model.gamemanager.IModel;
import de.hawh.beta3.application.game.view.IControllerView;

public class Context implements State {

    State currentState;
    IConfig iconfig;
    IModel imodel;
    IControllerView icontrollerview;
    Start start;
    Waiting waiting;
    Game game;
    End end;
    Delete delete;


    @Override
    public void behavior(){

    }

    @Override
    public void setCurrentState(String state) {
        switch (state) {
            case "Start" -> {
                currentState = start;
                start.behavior();
            }
            case "Waiting" -> {
                currentState = waiting;
                waiting.behavior();
            }
            case "Game" -> {
                currentState = game;
                game.behavior();
            }
            case "End" -> {
                currentState = end;
                end.behavior();
            }
            case "Delete" -> {
                currentState = delete;
                delete.behavior();
            }
            /*default:
                currentState = start;
                start.behavior();*/
        }
    }
}
