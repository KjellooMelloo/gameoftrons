package de.hawh.beta3.application.game.controller.config;

import javafx.util.Pair;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Config implements IConfig {

    int[] configParameters = new int[8];
    Map<String,Object> allParams = new HashMap<>();
    HashMap<String, Pair<Integer, String>> controls = new HashMap<>();
    HashMap<Integer, String[]> playerKeysMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        IConfig config = new Config();
        config.loadConfigParameters();
    }

    public int[] loadConfigParameters() throws IOException {
        //#1 defaultPlayerCount     #2 maxWaitingTime     #3 gameSpeed     #4 gridSize
        //#5 rangeStart       #6 rangeEnd       #7 remote       #8 partner
        int[] gameParameters = new int[8];
        FileReader reader = new FileReader("gameoftrons.properties");
        Properties props = new Properties();
        props.load(reader);
        gameParameters[0] = Integer.parseInt((String) props.get("defaultPlayerCount")) ;
        gameParameters[1] = Integer.parseInt((String) props.get("maxWaitingTime"));
        gameParameters[2] = Integer.parseInt((String) props.get("gameSpeed"));
        gameParameters[3] = Integer.parseInt((String) props.get("gridSize"));
        gameParameters[4] = Integer.parseInt((String) props.get("rangeStart"));
        gameParameters[5] = Integer.parseInt((String) props.get("rangeEnd"));
        boolean r = Boolean.parseBoolean((String) props.get("remote"));
        gameParameters[6] = r ? 1 : 0;
        boolean p = Boolean.parseBoolean((String) props.get("partner"));
        gameParameters[7] = p ? 1 : 0;
        configParameters = gameParameters;
        for (int i=0; i<configParameters.length; i++){
            System.out.println(configParameters[i]);
        }
        return configParameters;
    }

    public void loadConfigControls(){
        //#1 rangeStart     #2 rangeEnd     #3 remote       #4 partner      #5 controls
        int[] otherParameters = new int[5];
    }

    public int loadDefaultPlayerCount(){
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
        return configParameters[4];
    }

    public int getRangeEnd(){
        return configParameters[5];
    }

    public boolean getRemote(){
        return Boolean.parseBoolean(String.valueOf(configParameters[6]));
    }

    public int[] getConfigParameters(){
        return configParameters;
    }
}
