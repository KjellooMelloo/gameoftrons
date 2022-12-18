package de.hawh.beta3.application.game.model.gamemanager;

public interface IModel {

    IModel getInstance();

    void startGame(int numPlayers, int size, int gameSpeed);

    void changePlayerDirection(int id, String action);
}
