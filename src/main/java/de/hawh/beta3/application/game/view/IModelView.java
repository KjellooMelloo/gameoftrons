package de.hawh.beta3.application.game.view;

public interface IModelView {


    void updatePlayer(int playerID, int newX, int newY, int newOrientation);

    void informUser(String information);

    void updateNumPlayers(int newPlayerNum);

}
