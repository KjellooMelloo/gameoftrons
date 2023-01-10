package de.hawh.beta3.application.game.view.Screen;

import javafx.scene.paint.Color;

public class ScreenCommons {
    protected Color[] colors;
    //TODO: Add Controller Interface

    public ScreenCommons(){
        this.colors=new Color[]{Color.AQUA, Color.CRIMSON, Color.YELLOW, Color.LIME, Color.FUCHSIA, Color.ORANGE};
    }

    protected Color getColor(int playerID){
        if(playerID > 5) throw new IllegalArgumentException("PlayerIDs can't be larger than 5");
        return colors[playerID];
    }
}
