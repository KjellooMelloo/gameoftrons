package de.hawh.beta3.application.game.controller.config;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Config implements IConfig {

    int[] configParameters = new int[7];

    int playerCount = 0; //stateMachine.handleInputPlayerCount();
    //IntStream range = IntStream.of(2, 6);
    List<Integer> range = List.of(2,3,4,5,6);
    int defaultPlayerCount = 4;
    float maxWaitingTime; //cast zu int
    CONTROLS controls;
    float gameSpeed; //cast zu int
    //DIMENSIONS gridSize; //am einfachsten wäre int
    int[] gridSize = new int[2];

    public int[] loadConfigParameters(){
        String data;
        Map<String, String> parameters = new HashMap<>();
        String val;
        String regex = "(\"/(\\\\S+[^=])(=)(\\\\S+)/\")";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher;
        //Set<String> params = Stream.of(new String[] {"size","gameSpeed","maxWaitingTime", "playerCount", "controls"}).collect(Collectors.toSet());
        String[] params = new String[] {"size","gameSpeed","maxWaitingTime", "playerCount", "controls"};
        //parse file with Scanner
        Scanner scanner = new Scanner("gameoftrons.properties");
        for (String p : params) {
            while (scanner.hasNextLine()) {
                data = scanner.nextLine();
                if (data.contains(p)) {
                    matcher = pattern.matcher(data);
                    val = matcher.group(3);
                    parameters.put(p, val);
                }
            }
        }



        if (!range.contains(playerCount)){
            loadDefaultPlayerCount();
        }
        //todo
        //aus Config Werte laden und in Array speichern

        return new int[7];
    }

    public int loadDefaultPlayerCount(){
        playerCount=defaultPlayerCount;
        return playerCount;
    }

    public int getPlayerCount(){
        return configParameters[0];
    }

    public int getWaitingTime(){
        return configParameters[1];
    }

    public CONTROLS getControls(){
        if (configParameters[2] == 0){
            return CONTROLS.LEFT_RIGHT;
        } else if (configParameters[2] == 1){
            return CONTROLS.A_D;
        } else if (configParameters[2] == 2){
            return CONTROLS.B_M;
        } else if (configParameters[2] == 3){
            return CONTROLS.X_V;
        } else if (configParameters[2] == 4){
            return CONTROLS.T_U;
        } else if (configParameters[2] == 5){
            return CONTROLS.I_P;
        } else {
            return null;
        }
    }

    public int getGameSpeed(){
        return configParameters[3];
    }

    public int getGridSize() {
        return configParameters[4];
    }

    /*public int[] getGridSize() {
        gridSize[0] = configParameters[4];
        gridSize[1] = configParameters[4];
        return gridSize;
    }*/

}
