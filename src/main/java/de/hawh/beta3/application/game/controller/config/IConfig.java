package de.hawh.beta3.application.game.controller.config;

public interface IConfig {

    int[] loadConfigParameters();
    int loadDefaultPlayerCount();

    int setPlayerCount(int playerCount);
}
