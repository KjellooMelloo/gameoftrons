package de.hawh.beta3.application.game.view.Screen;

import de.hawh.beta3.application.game.view.IControllerView;
import de.hawh.beta3.application.game.view.IModelView;

import java.util.Map;

public class IViewImpl implements IControllerView, IModelView {

    private static IViewImpl instance = null;
    private ScreenManager screen;

    private IViewImpl() {
        this.screen = new ScreenManager();
    }

    public static IViewImpl getInstance() {
        if (instance == null)
            instance = new IViewImpl();

        return instance;
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

    @Override
    public void showScreen(String screenName) {
        if (screenName.equals("countdown")) {
            screen.showCountDown();
        } else screen.drawScreen(screenName);
    }

    @Override
    public void startGame(int playerCount, int gameFieldSize) {
        screen.updateView(playerCount, gameFieldSize);

    }

    @Override
    public void notifyGameResult(int winnerID) {
        screen.endGame(winnerID);
    }

    @Override
    public void setPlayerKeys(Map<Integer, String[]> playerKeysMap) {
        ScreenCommons.setPlayersKeyMap(playerKeysMap);

    }

    public ScreenManager getScreen() {
        return screen;
    }
}
