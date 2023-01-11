package de.hawh.beta3.application.game.controller.statemachine;

public class Delete implements State {

    public Delete(Context context){
        this.behavior(context);
    }

    @Override
    public void behavior(Context context){
        //to do
        //opt: showScreen("Delete")
        //deleteGameInstance()
        context.setCurrentState(new Start(context));
    }

    void deleteGameInstance(){
        //iModel.playerList.reset();
        //iModel.lobby.numPlayers = 0;
    }
}
