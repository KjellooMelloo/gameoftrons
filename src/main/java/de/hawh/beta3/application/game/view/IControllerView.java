package de.hawh.beta3.application.game.view;

import java.util.Map;

public interface IControllerView {
    void showScreen(String screenName);

    void setGameFieldSize(int gameFieldSize);

    void notifyGameResult(int winnerID);

    void setPlayerKeys(Map<Integer, String[]> playerKeysMap);
}
