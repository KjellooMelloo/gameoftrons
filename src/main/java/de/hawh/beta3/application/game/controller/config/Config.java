package de.hawh.beta3.application.game.controller.config;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Config implements IConfig {

    int[] configParameters = new int[4];
    Map<String, String> parameters = new HashMap<>();


    //add to config
    //playerCount & defaultPlayerCount & range
    //maxWaitingTime
    //gameSpeed
    //flag partner
    /*int playerCount = 0; //stateMachine.handleInputPlayerCount();
    //IntStream range = IntStream.of(2, 6);
    List<Integer> range = List.of(2,3,4,5,6);
    int defaultPlayerCount = 4;
    float maxWaitingTime; //cast zu int
    CONTROLS controls;
    float gameSpeed; //cast zu int
    //DIMENSIONS gridSize; //am einfachsten wäre int
    int[] gridSize = new int[2];*/

    public int[] loadConfigParameters(){
        String data;
        String val;
        String regex = "(\"/(\\\\S+[^=])(=)(\\\\S+)/\")";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher;
        String[] params = new String[] {"size","gameSpeed","maxWaitingTime", "playerCount", "controls"};
        //parse config file with Scanner for each parameter
        //put parameter & value in map

        for (String p : params) {
            Scanner scanner = new Scanner("gameoftrons.properties");
            while (scanner.hasNextLine()) {
                data = scanner.nextLine();
                if (data.contains(p)) {
                    matcher = pattern.matcher(data);
                    val = matcher.group(3);
                    parameters.put(p, val);
                }
            }
        }

        //extract needed values from map
        //#1 playerCount-->userInput    #2 defaultPlayerCount-->4    3# rangeStart-->2     #4 rangeEnd-->6
        //#5 maxWaitingTime-->60(in s)    #6 controls-->1-6       #7 gameSpeed-->3      #8 gridSize-->20
        //into array int[4]
        //#1playerCount    #2maxWaitingTime    #3gameSpeed    #4gridSize
        String playerCount = parameters.get("playerCount");
        String rangeStart = parameters.get("rangeStart");
        String rangeEnd = parameters.get("rangeEnd");
        if (Integer.parseInt(rangeStart)<=Integer.parseInt(playerCount)&&Integer.parseInt(playerCount)<=Integer.parseInt(rangeEnd)){
            configParameters[0]=Integer.parseInt(playerCount);
        } else {
            loadDefaultPlayerCount();
        }
        configParameters[1]=Integer.parseInt(parameters.get("maxWaitingTime"));
        configParameters[2]=Integer.parseInt(parameters.get("gameSpeed"));
        configParameters[3]=Integer.parseInt(parameters.get("gridSize"));

        return configParameters;
    }

    public int loadDefaultPlayerCount(){
        String defaultPlayerCount = parameters.get("defaultPlayerCount");
        configParameters[0]=Integer.parseInt(defaultPlayerCount);
        return configParameters[0];
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
        return configParameters[2];
    }

    public int getGridSize() {
        return configParameters[3];
    }

    /*public int[] getGridSize() {
        gridSize[0] = configParameters[4];
        gridSize[1] = configParameters[4];
        return gridSize;
    }*/

}
