package de.hawh.beta3.application.game.view;

import java.util.Map;

public interface IControllerView {
    void showScreen(String screenName);

    void startGame(int playerCount, int gameFieldSize);

    void notifyGameResult(int winnerID);

    void setPlayerKeys(Map<Integer, String[]> playerKeysMap);
}
