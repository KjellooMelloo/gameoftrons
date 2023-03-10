package de.hawh.beta3.application.game.controller.statemachine;

import de.hawh.beta3.application.game.controller.config.Config;
import de.hawh.beta3.application.game.controller.config.IConfig;
import de.hawh.beta3.application.game.model.gamemanager.GameManager;
import de.hawh.beta3.application.game.model.gamemanager.IModel;
import de.hawh.beta3.application.game.view.IControllerView;
import de.hawh.beta3.application.game.view.Screen.IViewImpl;
import javafx.util.Pair;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Context implements IContext {

    private static Context context = new Context();

    IControllerView iView = IViewImpl.getInstance();
    IModel iModel = GameManager.getInstance();
    IConfig iConfig = new Config();

    //Variablen aus der Config
    int[] configParameters = new int[8];
    HashMap<String, Pair<Integer, String>> controls = new HashMap<>();
    HashMap<Integer, String[]> playerKeysMap = new HashMap<>();


    private State currentState;

    private Context(){
        context = this;
        currentState = new Start();
    }

    public static Context getInstance() {
        return context;
    }

    public void initialize(IModel iModel) {
        this.iModel = iModel;
        iView = IViewImpl.getInstance();
    }

    @Override
    public void setCurrentState(String state){
        this.currentState = getStateFromString(state);
    }

    @Override
    public void handleInputPlayerCount(int playerCount) throws IOException {
        iConfig.loadConfigParameters();
        iConfig.setPlayerCount(playerCount);
        configParameters = iConfig.getConfigParameters();

        playerKeysMap = iConfig.loadControls(); //make Map for View id --> {"A","D"}
        transformControls(); //transform Map for efficient handleDirectionKeyboardInput()

        iView.setPlayerKeys(playerKeysMap);
        iModel.join(configParameters[0],configParameters[1]); //playerCount, maxWaitingTime
        if (iConfig.getRemote()){
            context.setCurrentState("WAITING");
            iView.showScreen("lobby");  //Sollte hier glaube ich noch gemacht werden
            //todo hier schl??gt es fehl
        } else {
            //todo setNumPlayers(playerCount); --> Model initialisiert 4 Spieler, k??nnen dann mit aktivierten Tasten spielen
            iModel.setNumPlayers(configParameters[0]);
            setCurrentState("GAME");
        }
    }

    @Override
    public void handleOfflineButton() throws IOException {
        iConfig.setOffline();
    }

    @Override
    public void handleWaitingButtonClick(){
        iModel.cancelWait();
        context.setCurrentState("DELETE");
    }

    @Override
    public void notifyCountdownOver(){
        iModel.startGame(context.configParameters[3], context.configParameters[2]); //int gridSize, int gameSpeed
        iView.startGame(context.configParameters[0], context.configParameters[3]); //int playerCount, int gridSize
    }

    @Override
    public void handleDirectionKeyboardInput(String key){

        //todo pr??fen, ob id < playerCount oder schon bei transformControls()-Methode
        if (controls.containsKey(key)){ //pr??ft, ob Tastenbelegung aktiviert ist f??r key
            iModel.changePlayerDirection(controls.get(key).getKey(),controls.get(key).getValue()); //id, direction
        }
    }

   @Override
    public void endGame(int result){
        iView.notifyGameResult(result);
        context.setCurrentState("END");
    }

    public State getStateFromString(String state){
        switch (state){
            case "START":
                return new Start();
            case "WAITING":
                return new Waiting();
            case "GAME":
                return new Game();
            case "END":
                return new End();
            case "DELETE":
                return new Delete();
            default:
                return new Start();
        }
    }

    public void transformControls(){
       //id --> {"A","D"} String[]
       //"A" --> {id, "left"} Pair

        int id;
        String key_l;
        String key_r;

       for (Map.Entry<Integer, String[]> entry : playerKeysMap.entrySet()){
           id = entry.getKey();
           key_l = entry.getValue()[0];
           key_r = entry.getValue()[1];
           controls.put(key_l,new Pair<>(id,"left"));
           controls.put(key_r,new Pair<>(id,"right"));
       }
    }
}
