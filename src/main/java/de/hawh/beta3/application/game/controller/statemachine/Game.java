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
        //schleife handleDirectionKeyboardInput()
        //if (endGame(int result))
        //      context.setCurrentState(new End(context));
    }


    //todo sicherheitsmodus überlegen, sodass nur aktivierte Tasten funktionieren --> 4 spieler, nur entsprechende Tastenbelegungen aktiviert
    public String handleDirectionKeyboardInput(String key){
        if (key.equals("A") || key.equals("LEFT") || key.equals("G")
                || key.equals("DIGIT1") || key.equals("DIGIT5") || key.equals("I")){
            return "LEFT";
        }
        else if (key.equals("D") || key.equals("RIGHT") || key.equals("J")
                || key.equals("DIGIT3") || key.equals("DIGIT9") || key.equals("P")) {
            return "RIGHT";
        } else {
            return "invalid key";
        }
    }

    public void endGame(int result){}

}
