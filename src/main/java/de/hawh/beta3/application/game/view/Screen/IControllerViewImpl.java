package de.hawh.beta3.application.game.view.Screen;

import de.hawh.beta3.application.game.view.IControllerView;

public class IControllerViewImpl implements IControllerView {

    ScreenManager screen;
    private static IControllerViewImpl iControllerViewImpl = new IControllerViewImpl(new ScreenManager());

    private IControllerViewImpl(ScreenManager screen){
        this.screen = screen;
    }

    public static IControllerViewImpl getInstance() {
        return iControllerViewImpl;
    }


    /**public void IControllerViewImpl(ScreenManager screen) {
        this.screen = screen;
    }**/

    @Override
    public void showScreen(String screenName) {
        if (screenName.equals("countdown")) {
            screen.showCountDown();
        } else screen.drawScreen(screenName);
    }

    @Override
    public void setGameFieldSize(int gameFieldSize) {
        screen.updateView(gameFieldSize);

    }

    @Override
    public void notifyGameResult(int winnerID) {
        screen.endGame(winnerID);
    }
}
