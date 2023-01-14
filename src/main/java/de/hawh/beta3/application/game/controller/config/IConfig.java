package de.hawh.beta3.application.game.controller.config;

import javafx.util.Pair;

import java.util.HashMap;

public interface IConfig {

    int[] loadConfigParameters();
    int loadDefaultPlayerCount();
    HashMap<String, Pair<Integer, String>> loadControls();

    int setPlayerCount(int playerCount);
}
