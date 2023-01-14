package de.hawh.beta3.application;

import de.hawh.beta3.application.game.controller.IModelController;
import de.hawh.beta3.application.game.model.gamemanager.IModel;
import de.hawh.beta3.application.game.view.IModelView;
import de.hawh.beta3.application.game.view.Screen.EndScreen;
import de.hawh.beta3.application.game.view.Screen.ScreenManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.FileReader;
import java.util.Properties;

public class GameOfTrons extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Config lesen
        System.err.println("# Config wird gelesen");
        FileReader fr = new FileReader("gameoftrons.properties");
        Properties p = new Properties();
        p.load(fr);
        boolean remote = Boolean.parseBoolean(p.getProperty("remote"));
        System.err.println("# Es wird " + (remote ? "remote" : "lokal") + " gespielt!");

        //TODO Wenn remote:
            //TODO Nameserver starten

            //TODO Middleware aufbauen/ starten

            //TODO Callee Objekte registrieren

        // MVC Referenzen
        //IModel model = MVCFactory.getInterface("IModel", remote);
        //IModelView modelView = MVCFactory.getInterface("IModelView", remote);
        //IModelController modelController  = MVCFactory.getInterface("IModelController", remote);

        //TODO initialize für Interfaces mit obigen Referenzen

        // Stage bauen
        stage.setWidth(javafx.stage.Screen.getPrimary().getBounds().getWidth() / 1.5);
        stage.setHeight(javafx.stage.Screen.getPrimary().getBounds().getHeight() / 1.5);

        ScreenManager gameScreen = new ScreenManager();
        gameScreen.drawScreen("start");

        // configure and show stage
        stage.setTitle("Game of Trons - Not so Light BiCycles");
        stage.setScene(gameScreen.getScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
