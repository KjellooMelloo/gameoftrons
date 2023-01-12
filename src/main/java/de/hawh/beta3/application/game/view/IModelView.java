package de.hawh.beta3.application.game.view;

import de.hawh.beta3.application.game.view.Screen.Screen;

public interface IModelView {


     void updatePlayer(int playerID,int newX,int newY,int newOrientation);
     void informUser(String information);
     void updateNumPlayers(int newPlayerNum);

}
