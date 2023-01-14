package de.hawh.beta3.application.game.controller.statemachine;

import de.hawh.beta3.application.game.controller.config.IConfig;
import de.hawh.beta3.application.game.model.gamemanager.IModel;
import de.hawh.beta3.application.game.view.IControllerView;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

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
        if (key.equals("A") || key.equals("LEFT") || key.equals("G")
                || key.equals("DIGIT1") || key.equals("DIGIT5") || key.equals("I")){
            iModel.changePlayerDirection(0, "left");
        }
        else if (key.equals("D") || key.equals("RIGHT") || key.equals("J")
                || key.equals("DIGIT3") || key.equals("DIGIT9") || key.equals("P")) {
            iModel.changePlayerDirection(0, "right");
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
