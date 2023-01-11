package de.hawh.beta3.application.game.view.Screen;

import javafx.application.Application;
import javafx.stage.Stage;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage){

        stage.setFullScreen(true);
        Screen gameScreen = new Screen();
        gameScreen.resetScreen();
        gameScreen.drawScreen("countdown");
        CountdownScreen cs = (CountdownScreen) gameScreen.getScreens().get("countdown");
        cs.startCountdown();









        // configure and show stage
        stage.setTitle("TRON - Light Cycles");
        stage.setScene(gameScreen.getScene());
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}