package de.hawh.beta3.application.game.controller.statemachine;

import de.hawh.beta3.application.game.controller.config.IConfig;
import de.hawh.beta3.application.game.model.gamemanager.IModel;
import de.hawh.beta3.application.game.view.IControllerView;

public class Start implements State {

    IControllerView iView;
    IModel iModel;
    IConfig iConfig;
    public Start(Context context){
        this.behavior(context);
    }

    @Override
    public void behavior(Context context){
        //to do
        //iView.showScreen("Start");
        //handleInputPlayerCount();
        iConfig.loadConfigParameters();
        // applicationStub.register() --> int ID
        //iModel.join(int numPlayers);
        //iView.updateNumPlayers(int numPlayers);
        context.setCurrentState(new Waiting(context));
    }

    public int handleInputPlayerCount(){
        return 0;
    }
}
