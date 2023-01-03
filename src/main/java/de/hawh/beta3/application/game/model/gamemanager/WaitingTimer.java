package de.hawh.beta3.application.game.model.gamemanager;

import java.util.TimerTask;

public class WaitingTimer extends TimerTask {

    private GameManager manager = GameManager.getInstance();

    /**
     * When the waiting timer ended the game gets cancelled
     */
    @Override
    public void run() {
        manager.cancelWait();
    }
}
