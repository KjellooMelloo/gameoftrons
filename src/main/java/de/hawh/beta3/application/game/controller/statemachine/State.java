package de.hawh.beta3.application.game.controller.statemachine;


public interface State {

    void behavior(Context context);

    //void setCurrentState(String state);
}
