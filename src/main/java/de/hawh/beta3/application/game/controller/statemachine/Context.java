package de.hawh.beta3.application.game.controller.statemachine;

import de.hawh.beta3.application.game.controller.config.IConfig;
import de.hawh.beta3.application.game.model.gamemanager.IModel;
import de.hawh.beta3.application.game.view.IControllerView;
import javafx.util.Pair;

import java.util.ArrayList;

public class Context implements IContext {

    Context context;
    int[] configParameters;
    IControllerView iView;
    IModel iModel;
    IConfig iConfig;

    private State currentState;

    public Context(){
        context = this;
        currentState = new Start(context);
    }
    private State getState(){
        return currentState;
    }

    public void setCurrentState(String state){
        this.currentState = getStateFromString(state);
    }

    public void handleInputPlayerCount(int playerCount){
        iConfig.setPlayerCount(playerCount);
        int[] configParameters = iConfig.loadConfigParameters();
        context.configParameters = configParameters;

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
        ArrayList<Pair<String, String>> keyToId = new ArrayList<>(6);
        keyToId.add(0, new Pair<>("A","D"));
        keyToId.add(1, new Pair<>("LEFT","RIGHT"));
        keyToId.add(2, new Pair<>("G","J"));
        keyToId.add(3, new Pair<>("DIGIT1","DIGIT3"));
        keyToId.add(4, new Pair<>("DIGIT5","DIGIT9"));
        keyToId.add(5, new Pair<>("I","P"));

        for (int i=0; i<6; i++){
            Pair<String,String> pair = keyToId.get(i);
            if (pair.getKey().equals(key)){
                iModel.changePlayerDirection(i,"left");
            } else if (pair.getValue().equals(key)){
                iModel.changePlayerDirection(i,"right");
            }
        }
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
