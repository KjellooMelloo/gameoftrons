package de.hawh.beta3.application.game.controller.statemachine;

public class Game implements State {

    public Game(Context context){
        this.behavior(context);
    }

    @Override
    public void behavior(Context context){
        //to do
        //showScreen("Countdown")
        //--> if (iViewController.notifyCountdownOver())
        //      opt: setGameFieldParameters()
        //      iModel.startGame(int size, int speed)
        //      iView.setGameFieldSize(int size)
        // schleife handleDirectionKeyboardInput()
        // if (endGame(int result))
        context.setCurrentState(new End(context));
    }

    public String handleDirectionKeyboardInput(){
        return "";
    }
}
