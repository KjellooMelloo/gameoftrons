package de.hawh.beta3.application.game.view;

public interface IControllerView {
    void showScreen(String screenName);
    void setGameFieldSize(int gameFieldSize);
    void notifyGameResult(int winnerID);
}
