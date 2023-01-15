package de.hawh.beta3.application.game.controller.config;

import javafx.util.Pair;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public interface IConfig {

    int[] loadConfigParameters() throws IOException;
    int loadDefaultPlayerCount();
    HashMap<Integer, String[]> loadControls() throws IOException;
    int[] getConfigParameters();

    int setPlayerCount(int playerCount);
    void setOffline() throws IOException;

    boolean getRemote();
}
