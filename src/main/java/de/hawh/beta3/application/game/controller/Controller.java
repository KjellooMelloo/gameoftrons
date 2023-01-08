package de.hawh.beta3.application.game.controller;

import de.hawh.beta3.application.game.view.IControllerView;
import org.w3c.dom.ranges.Range;

import java.util.*;
import java.util.function.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Controller {

    IControllerView controllerView;
    StateMachine stateMachine;
    ArrayList<Object> configParameters;

    private class StateMachine {
        Config config;

        public int handleInputPlayerCount(){
            return 0;
        }
    }

    private class Config {
        int playerCount = stateMachine.handleInputPlayerCount();
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

    enum CONTROLS {

    }

    enum DIMENSIONS {

    }
}
