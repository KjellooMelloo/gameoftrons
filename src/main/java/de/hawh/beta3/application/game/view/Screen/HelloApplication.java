package de.hawh.beta3.application.game.view.Screen;

import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage){

        stage.setFullScreen(true);
        Screen gameScreen = new Screen();
        ScreenCommons sc = new ScreenCommons();
        gameScreen.drawScreen("end");
        EndScreen es = (EndScreen)gameScreen.getScreens().get("end");
        es.setWinner(2);














        // configure and show stage
        stage.setTitle("TRON - Light Cycles");
        stage.setScene(gameScreen.getScene());
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}