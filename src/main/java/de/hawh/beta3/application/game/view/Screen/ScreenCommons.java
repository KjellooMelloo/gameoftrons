package de.hawh.beta3.application.game.view.Screen;

import de.hawh.beta3.application.game.controller.statemachine.Context;
import de.hawh.beta3.application.game.controller.statemachine.IContext;
import javafx.scene.paint.Color;

public class ScreenCommons {
    private final Color[] colors;
    private final String[] colorNames;

    //private IContext controller = new Context();

    public ScreenCommons() {
        this.colors = new Color[]{Color.AQUA, Color.CRIMSON, Color.YELLOW, Color.LIME, Color.FUCHSIA, Color.ORANGE};
        this.colorNames = new String[]{"blue", "red", "yellow", "green", "pink", "orange"};
    }

    public Color getColor(int playerID) {
        if (playerID > 5) throw new IllegalArgumentException("PlayerIDs can't be larger than 5");
        return colors[playerID];
    }

    public String getColorName(int playerID) {
        if (playerID > 5) throw new IllegalArgumentException("PlayerIDs can't be larger than 5");
        return colorNames[playerID];
    }

}
