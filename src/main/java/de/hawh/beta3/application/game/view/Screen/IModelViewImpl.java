package de.hawh.beta3.application.game.view.Screen;

import de.hawh.beta3.application.game.view.IModelView;

public class IModelViewImpl implements IModelView {

    ScreenManager screen = new ScreenManager();


    public IModelViewImpl(ScreenManager screen) {
        this.screen = screen;
    }

    @Override
    public void updatePlayer(int playerID, int newX, int newY, int newOrientation) {
        screen.updatePlayer(playerID, newX, newY, newOrientation);
    }

    @Override
    public void informUser(String information) {
        screen.informUser(information);
    }

    @Override
    public void updateNumPlayers(int newPlayerNum) {
        screen.setNumPlayers(newPlayerNum);
        screen.updateCurrentPlayerID(newPlayerNum - 1);
    }
}
