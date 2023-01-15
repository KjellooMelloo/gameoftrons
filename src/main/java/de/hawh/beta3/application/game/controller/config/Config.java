package de.hawh.beta3.application.game.controller.config;

import javafx.util.Pair;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Config implements IConfig {

    int[] configParameters = new int[4];
    Map<String,Object> allParams = new HashMap<>();
    HashMap<String, Pair<Integer, String>> controls = new HashMap<>();
    HashMap<Integer, String[]> playerKeysMap = new HashMap<>();


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
        //int playerCount = Integer.parseInt(parameters.get("playerCount"));
        //allParams.put("playerCount", playerCount);
        int defaultPlayerCount = Integer.parseInt(parameters.get("defaultPlayerCount"));
        allParams.put("defaultPlayerCount", defaultPlayerCount);
        int rangeStart = Integer.parseInt(parameters.get("rangeStart"));
        allParams.put("rangeStart", rangeStart);
        int rangeEnd = Integer.parseInt(parameters.get("rangeEnd"));
        allParams.put("rangeEnd", rangeEnd);
        int maxWaitingTime = Integer.parseInt(parameters.get("maxWaitingTime"));
        allParams.put("maxWaitingTime", maxWaitingTime);
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

        configParameters[1]=maxWaitingTime;
        configParameters[2]=gameSpeed;
        configParameters[3]=gridSize;

        return configParameters;
    }

    public int loadDefaultPlayerCount(){
        configParameters[0]=(int)allParams.get("defaultPlayerCount");
        return configParameters[0];
    }

    public int setPlayerCount(int playerCount){
        if (getRangeStart()<=playerCount&&playerCount<=getRangeEnd()){
            configParameters[0]=playerCount;
            return playerCount;
        } else {
            return loadDefaultPlayerCount();
        }
    }

    public int getPlayerCount(){
        return configParameters[0];
    }

    public int getWaitingTime(){
        return configParameters[1];
    }

    public List<CONTROLS> getControls(){
        List<CONTROLS> controls = new ArrayList<>();

        for (int i=1; i<=getPlayerCount(); i++){
            if (i==1){
                controls.add(CONTROLS.A_D);
            } else if (i==2){
                controls.add(CONTROLS.LEFT_RIGHT);
            } else if (i==3){
                controls.add(CONTROLS.G_J);
            } else if (i==4){
                controls.add(CONTROLS.DIGIT1_DIGIT3);
            } else if (i==5){
                controls.add(CONTROLS.DIGIT5_DIGIT9);
            } else {
                controls.add(CONTROLS.I_P);
            }
        } return controls;
    }

    /**@Override
    public HashMap<String, Pair<Integer, String>> loadControls(){
        //HashMap<String key, Pair<int id, String action>> controls
        String key;
        int id;
        String action;
        String line;
        Scanner scanner = new Scanner("gameoftrons.properties");
        Matcher matcher;
        String regex;
        if ((boolean)allParams.get("remote")){
            regex = "(\"p(0)_(l{1}|r{1})=(\\S+)\")";
        } else {
            regex = "(\"p(\\d{1})_(l{1}|r{1})=(\\S+)\")";
        }
        Pattern pattern = Pattern.compile(regex);
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            matcher = pattern.matcher(line);
            if (matcher.matches()){
                id = Integer.parseInt(matcher.group(1));
                action = matcher.group(2);
                if (action.equals("l")){
                    action="left";
                } else {
                    action="right";
                }
                key = matcher.group(3);
                controls.put(key, new Pair<>(id,action));
            }
        }

        return controls;
    }**/

    @Override
    public HashMap<Integer, String[]> loadControls(){
        int id;
        String direction;
        String key_l = "";
        String key_r;

        String line;
        Scanner scanner = new Scanner("gameoftrons.properties");
        Matcher matcher;
        String regex = "(\"p(\\d{1})_(l{1}|r{1})=(\\S+)\")";
        Pattern pattern = Pattern.compile(regex);
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            matcher = pattern.matcher(line);
            if (matcher.matches()) {
                id = Integer.parseInt(matcher.group(1));
                direction = matcher.group(2);
                if (direction.equals("l")) {
                    key_l = matcher.group(3);
                } else {
                    key_r = matcher.group(3);
                    playerKeysMap.put(id, new String[]{key_l, key_r});
                }
            }
        }
        return playerKeysMap;
    }

    public int getGameSpeed(){
        return configParameters[2];
    }

    public int getGridSize() {
        return configParameters[3];
    }

    public int getRangeStart(){
        return (int)allParams.get("rangeStart");
    }

    public int getRangeEnd(){
        return (int)allParams.get("rangeEnd");
    }

    public boolean getRemote(){
        return (boolean)allParams.get("remote");
    }

    public int[] getConfigParameters(){
        return configParameters;
    }
}
