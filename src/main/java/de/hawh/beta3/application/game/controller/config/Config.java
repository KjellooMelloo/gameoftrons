package de.hawh.beta3.application.game.controller.config;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Config implements IConfig {

    int[] configParameters = new int[4];
    Map<String,Object> allParams = new HashMap<>();

    //add to config
    //playerCount & defaultPlayerCount & range
    //maxWaitingTime
    //gameSpeed
    //flag partner


    public int[] loadConfigParameters(){
        Map<String, String> parameters = new HashMap<>();

        String data;
        String val;
        String regex = "(\"/(\\\\S+[^=])(=)(\\\\S+)/\")";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher;
        String[] params = new String[] {"size","gameSpeed","maxWaitingTime", "playerCount", "controls"};
        //parse config file with Scanner for each parameter
        //put parameter & value as Strings in map

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

        //save needed values from map in correct data format
        //#1 playerCount-->userInput    #2 defaultPlayerCount-->4    3# rangeStart-->2     #4 rangeEnd-->6
        //#5 maxWaitingTime-->60(in s)    #6 controls-->1-6       #7 gameSpeed-->3      #8 gridSize-->20
        int playerCount = Integer.parseInt(parameters.get("playerCount"));
        allParams.put("playerCount", playerCount);
        int defaultPlayerCount = Integer.parseInt(parameters.get("defaultPlayerCount"));
        allParams.put("defaultPlayerCount", defaultPlayerCount);
        int rangeStart = Integer.parseInt(parameters.get("rangeStart"));
        allParams.put("rangeStart", rangeStart);
        int rangeEnd = Integer.parseInt(parameters.get("rangeEnd"));
        allParams.put("rangeEnd", rangeEnd);
        int mayWaitingTime = Integer.parseInt(parameters.get("maxWaitingTime"));
        allParams.put("mayWaitingTime", mayWaitingTime);
        int gameSpeed = Integer.parseInt(parameters.get("gameSpeed"));
        allParams.put("gameSpeed", gameSpeed);
        int gridSize = Integer.parseInt(parameters.get("gridSize"));
        allParams.put("gridSize", gridSize);
        boolean remote = Boolean.parseBoolean(parameters.get("remote"));
        allParams.put("remote", remote);
        boolean partner = Boolean.parseBoolean(parameters.get("partner"));
        allParams.put("partner", partner);


        //game relevant info for view and model into array int[4]
        //#1playerCount    #2maxWaitingTime    #3gameSpeed    #4gridSize

        if (rangeStart<=playerCount&&playerCount<=rangeEnd){
            configParameters[0]=playerCount;
        } else {
            loadDefaultPlayerCount();
        }
        configParameters[1]=mayWaitingTime;
        configParameters[2]=gameSpeed;
        configParameters[3]=gridSize;

        return configParameters;
    }

    public int loadDefaultPlayerCount(){
        configParameters[0]=(int)allParams.get("defaultPlayerCount");
        return configParameters[0];
    }

    public int getPlayerCount(){
        return configParameters[0];
    }

    public int getWaitingTime(){
        return configParameters[1];
    }

    public List<CONTROLS> getControls(){
        List<CONTROLS> controls = new ArrayList<>();
        controls.add(CONTROLS.A_D);
        if ((boolean)allParams.get("remote") || (boolean)allParams.get("partner")){
            return controls;
        } else if (getPlayerCount()==2){
            controls.add(CONTROLS.LEFT_RIGHT);
            return controls;
        } else if (getPlayerCount()==3){
            controls.add(CONTROLS.LEFT_RIGHT);
            controls.add(CONTROLS.G_J);
            return controls;
        } else if (getPlayerCount()==4){
            controls.add(CONTROLS.LEFT_RIGHT);
            controls.add(CONTROLS.G_J);
            controls.add(CONTROLS.DIGIT1_DIGIT3);
            return controls;
        } else if (getPlayerCount()==5){
            controls.add(CONTROLS.LEFT_RIGHT);
            controls.add(CONTROLS.G_J);
            controls.add(CONTROLS.DIGIT1_DIGIT3);
            controls.add(CONTROLS.DIGIT5_DIGIT9);
            return controls;
        } else if (getPlayerCount()==6){
            controls.add(CONTROLS.LEFT_RIGHT);
            controls.add(CONTROLS.G_J);
            controls.add(CONTROLS.DIGIT1_DIGIT3);
            controls.add(CONTROLS.DIGIT5_DIGIT9);
            controls.add(CONTROLS.I_P);
            return controls;
        } else {
            return new ArrayList<>();
        }
    }

    public int getGameSpeed(){
        return configParameters[2];
    }

    public int getGridSize() {
        return configParameters[3];
    }

}
