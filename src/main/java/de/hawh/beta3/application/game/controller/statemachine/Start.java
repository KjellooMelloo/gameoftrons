package de.hawh.beta3.application.game.controller.statemachine;

import de.hawh.beta3.application.game.controller.config.Config;
import de.hawh.beta3.application.game.controller.config.IConfig;

public interface Start extends State {

    @Override
    void behavior();

    @Override
    void setCurrentState(String state);

    int handleInputPlayerCount();
}
