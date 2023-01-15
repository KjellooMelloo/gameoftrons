package de.hawh.beta3.application.game.controller.statemachine;

import de.hawh.beta3.application.game.controller.config.Config;
import de.hawh.beta3.application.game.controller.config.IConfig;
import de.hawh.beta3.application.game.model.gamemanager.GameManager;
import de.hawh.beta3.application.game.model.gamemanager.IModel;
import de.hawh.beta3.application.game.view.IControllerView;
import de.hawh.beta3.application.game.view.Screen.IViewImpl;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Context implements IContext {

    private static Context context = new Context();

    IControllerView iView = IViewImpl.getInstance();
    IModel iModel = GameManager.getInstance();
    IConfig iConfig = new Config();

    //Variablen aus der Config
    int[] configParameters;
    HashMap<String, Pair<Integer, String>> controls;
    HashMap<Integer, String[]> playerKeysMap;
    Pair<Integer, String> pair;
    int id;
    String action;
    String left;
    String right;


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

    public void setCurrentState(String state){
        this.currentState = getStateFromString(state);
    }

    public void handleInputPlayerCount(int playerCount){
        iConfig.setPlayerCount(playerCount);
        configParameters = iConfig.loadConfigParameters();
        controls = iConfig.loadControls();
        if (iConfig.getRemote()){
            //Map.Entry<String,Pair<Integer,String>> entry = controls.values().stream().anyMatch(p -> p.getValue().equals("left"));
            Optional<Map.Entry<String,Pair<Integer,String>>> entry = controls.entrySet().stream().filter(e->e.getValue().getValue().equals("left")).findFirst();
            entry.ifPresent(e -> left = e.getKey());
            Optional<Map.Entry<String,Pair<Integer,String>>> entry2 = controls.entrySet().stream().filter(e->e.getValue().getValue().equals("right")).findFirst();
            entry2.ifPresent(e -> right = e.getKey());
        }

        iView.setPlayerKeys(new HashMap<>());
        iModel.join(configParameters[0], configParameters[1]); //playerCount & maxWaitingTime
        context.setCurrentState("WAITING");
    }

    public void handleWaitingButtonClick(){
        iModel.cancelWait(); //LOKAL
        context.setCurrentState("DELETE");
    }

    public void notifyCountdownOver(){
        iModel.startGame(context.configParameters[3], context.configParameters[2]); //int gridSize, int gameSpeed
        iView.setGameFieldSize(context.configParameters[3]);
    }

    //todo sicherheitsmodus überlegen, sodass nur aktivierte Tasten funktionieren --> 4 spieler, nur entsprechende Tastenbelegungen aktiviert
    //LOKALE VERSION
    public void handleDirectionKeyboardInput(String key){

        pair = controls.get(key);
        id = pair.getKey();
        action = pair.getValue();

        iModel.changePlayerDirection(id,action);

    }

    //REMOTE
    public void handleDirectionKeyboardInput(int id, String key){

        if (key.equals(left)){
            iModel.changePlayerDirection(id, "left");
        }
        else if (key.equals(right)) {
            iModel.changePlayerDirection(id, "right");
        }
    }

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
}
