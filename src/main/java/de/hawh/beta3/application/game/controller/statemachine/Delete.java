package de.hawh.beta3.application.game.controller.statemachine;

import de.hawh.beta3.application.game.model.gamemanager.IModel;
import de.hawh.beta3.application.game.view.IControllerView;

public class Delete implements State {

    IControllerView iView;
    IModel iModel;
    Context context;

    public Delete(Context context){
        this.behavior(context);
    }

    @Override
    public void behavior(Context context){
        //to do
        //opt: showScreen("Delete")
        deleteGameInstance();
        context.setCurrentState(new Start(context));
    }

    void deleteGameInstance(){
        iModel.cancelWait(); //zum löschen
        //iModel.playerList.reset();
        //iModel.lobby.numPlayers = 0;
    }
}
