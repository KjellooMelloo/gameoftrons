package de.hawh.beta3.application.game.controller.statemachine;

import de.hawh.beta3.application.game.controller.config.IConfig;
import de.hawh.beta3.application.game.model.gamemanager.IModel;
import de.hawh.beta3.application.game.view.IControllerView;

public class Start implements State {

    IControllerView iView;
    IModel iModel;
    IConfig iConfig;
    Context context;

    public Start(Context context){
        this.behavior(context);
        this.context = context;
    }

    @Override
    public void behavior(Context context){
        //to do
        iView.showScreen("Start");
        //View: handleInputPlayerCount(int userInput);
        //? applicationStub.register() --> int ID
    }

    public void handleInputPlayerCount(int playerCount){
        iConfig.setPlayerCount(playerCount);
        int[] configParameters = iConfig.loadConfigParameters();
        context.configParameters = configParameters;

        iModel.join(configParameters[0]);
        context.setCurrentState(new Waiting(context));
    }
}
