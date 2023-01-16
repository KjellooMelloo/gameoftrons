package de.hawh.beta3.application.game.view.Screen;

import de.hawh.beta3.application.game.controller.statemachine.Context;
import de.hawh.beta3.application.game.controller.statemachine.IContext;
import de.hawh.beta3.application.game.factory.MVCFactory;
import javafx.scene.paint.Color;

import java.util.Map;

public class ScreenCommons {
    private static final Color[] colors = new Color[]{Color.AQUA, Color.CRIMSON, Color.YELLOW, Color.LIME, Color.FUCHSIA, Color.ORANGE};
    private static final String[] colorNames = new String[]{"blue", "red", "yellow", "green", "pink", "orange"};
    private static Map<Integer, String[]> playersKeyMap;
    public static IContext CONTROLLER = (IContext) MVCFactory.getInterface("IController", false);

    public IContext controller;


    public ScreenCommons() {
       controller = Context.getInstance();
    }

    public static Color getColor(int playerID) {
        if (playerID > 5) throw new IllegalArgumentException("PlayerIDs can't be larger than 5");
        return colors[playerID];
    }

    public static String getColorName(int playerID) {
        if (playerID > 5) throw new IllegalArgumentException("PlayerIDs can't be larger than 5");
        return colorNames[playerID];
    }

    public static void setPlayersKeyMap(Map<Integer, String[]> playersKeyMap) {
        ScreenCommons.playersKeyMap = playersKeyMap;
    }

    public static String getPlayerControls(int playerId) {
        if (!playersKeyMap.containsKey(playerId))
            throw new IllegalArgumentException("No controls available for player id");
        String[] controls = playersKeyMap.get(playerId);
        return "LEFT:" + controls[0] + " , " + "RIGHT:" + controls[1];
    }

}
