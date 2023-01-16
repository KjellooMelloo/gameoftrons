package de.hawh.beta3.application.game.controller.config;

import javafx.util.Pair;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Config implements IConfig {

    int[] configParameters = new int[8];
    HashMap<Integer, String[]> playerKeysMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        IConfig config = new Config();
        config.loadConfigParameters();
        config.loadControls();
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

    public void setOffline() throws IOException {
        Properties props = new Properties();
        InputStream in = getClass().getResourceAsStream("gameoftrons.properties");
        props.load(in);
        if (props.get("remote").equals("true")){
            props.remove("remote");
            props.setProperty("remote", "false");
        } else {
            props.remove("remote");
            props.setProperty("remote", "true");
        }
        props.store(new FileOutputStream("gameoftrons.properties"), null);
    }

    public int getPlayerCount(){
        return configParameters[0];
    }

    public int getWaitingTime(){
        return configParameters[1];
    }

    /**public List<CONTROLS> getControls(){
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
    }**/

    @Override
    public HashMap<Integer, String[]> loadControls() throws IOException {

        FileReader reader = new FileReader("gameoftrons.properties");
        Properties props = new Properties();
        props.load(reader);

        playerKeysMap.put(0, new String[]{(String) props.get("p0_l"), (String) props.get("p0_r")});
        playerKeysMap.put(1, new String[]{(String) props.get("p1_l"), (String) props.get("p1_r")});
        playerKeysMap.put(2, new String[]{(String) props.get("p2_l"), (String) props.get("p2_r")});
        playerKeysMap.put(3, new String[]{(String) props.get("p3_l"), (String) props.get("p3_r")});
        playerKeysMap.put(4, new String[]{(String) props.get("p4_l"), (String) props.get("p4_r")});
        playerKeysMap.put(5, new String[]{(String) props.get("p5_l"), (String) props.get("p5_r")});

        for (int i=0; i<playerKeysMap.size(); i++){
            System.out.println(Arrays.toString(playerKeysMap.get(i)));
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
        //in loadConfigParameters wird remote als 0 oder 1 geschrieben
        //parseBoolean schaut nach "true" und gibt sonst false
        //return Boolean.parseBoolean(String.valueOf(configParameters[6]));
        return configParameters[6] == 1;
    }

    public int[] getConfigParameters(){
        return configParameters;
    }
}
