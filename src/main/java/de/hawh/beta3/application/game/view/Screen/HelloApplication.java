package de.hawh.beta3.application.game.view.Screen;

import javafx.application.Application;
import javafx.stage.Stage;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws InterruptedException {


        stage.setWidth(javafx.stage.Screen.getPrimary().getBounds().getWidth() / 1.5);
        stage.setHeight(javafx.stage.Screen.getPrimary().getBounds().getHeight() / 1.5);

        ScreenManager gameScreen = new ScreenManager();
        gameScreen.drawScreen("end");
        EndScreen es = (EndScreen) gameScreen.getScreens().get("end");
        es.setWinner(2);
        gameScreen.informUser("bal");


        // configure and show stage
        stage.setTitle("TRON - Light Cycles");
        stage.setScene(gameScreen.getScene());
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}