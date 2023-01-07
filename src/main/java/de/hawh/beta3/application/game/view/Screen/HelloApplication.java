package de.hawh.beta3.application.game.view.Screen;

import javafx.application.Application;
import javafx.stage.Stage;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage){

        stage.setTitle("Hello!");

        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}