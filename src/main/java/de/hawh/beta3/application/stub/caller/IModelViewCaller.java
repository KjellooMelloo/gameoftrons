package de.hawh.beta3.application.stub.caller;

import de.hawh.beta3.application.game.view.IModelView;
import de.hawh.beta3.middleware.IMiddleware;
import de.hawh.beta3.middleware.Middleware;

public class IModelViewCaller implements IModelView {

    private IMiddleware mw = Middleware.getInstance();
    private int id = 1;

    //@Override
    public void updatePlayer(int playerID, int x, int y, int direction) {
        mw.invoke(id, "updatePlayer", new Object[]{playerID, x, y, direction});
    }

    //@Override
    public void updateNumPlayers(int numPlayers) {
        mw.invoke(id, "updateNumPlayers", new Object[]{numPlayers});
    }

    //@Override
    public void informUser(String message) {
        mw.invoke(id, "informUser", new Object[]{message});
    }
}
