package de.hawh.beta3.application.game.controller.statemachine;

import de.hawh.beta3.application.game.controller.config.IConfig;
import de.hawh.beta3.application.game.model.gamemanager.IModel;
import de.hawh.beta3.application.game.view.IControllerView;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Context implements IContext {

    Context context;
    IControllerView iView;
    IModel iModel;
    IConfig iConfig;

    //Variablen aus der Config
    int[] configParameters;
    HashMap<String, Pair<Integer, String>> controls;
    Pair<Integer, String> pair;
    int id;
    String action;
    String left;
    String right;


    private State currentState;

    public Context(){
        context = this;
        currentState = new Start(context);
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
                return new Start(context);
            case "WAITING":
                return new Waiting(context);
            case "GAME":
                return new Game(context);
            case "END":
                return new End(context);
            case "DELETE":
                return new Delete(context);
            default:
                return new Start(context);
        }
    }
}
