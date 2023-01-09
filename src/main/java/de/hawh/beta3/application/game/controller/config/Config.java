package de.hawh.beta3.application.game.controller.config;

import java.util.List;

public class Config implements IConfig {

    int playerCount = 0; //stateMachine.handleInputPlayerCount();
    //IntStream range = IntStream.of(2, 6);
    List<Integer> range = List.of(2,3,4,5,6);
    int defaultPlayerCount = 4;
    float maxWaitingTime;
    CONTROLS controls;
    float speed;
    DIMENSIONS gridSize;


    public int[] loadConfigParameters(){
        if (!range.contains(playerCount)){
            loadDefaultPlayerCount();
        }


        return new int[7];
    }

    public int loadDefaultPlayerCount(){
        playerCount=defaultPlayerCount;
        return playerCount;
    }
}
