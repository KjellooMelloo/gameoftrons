package de.hawh.beta3.application.game.controller.config;

import javafx.util.Pair;

import java.io.IOException;
import java.util.HashMap;

public interface IConfig {

    int[] loadConfigParameters() throws IOException;
    int loadDefaultPlayerCount();
    HashMap<Integer, String[]> loadControls();
    int[] getConfigParameters();

    int setPlayerCount(int playerCount);

    boolean getRemote();
}
