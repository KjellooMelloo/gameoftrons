package de.hawh.beta3.application;

import de.hawh.beta3.application.game.controller.statemachine.Context;
import de.hawh.beta3.application.game.controller.statemachine.IContext;
import de.hawh.beta3.application.game.model.gamemanager.GameManager;
import de.hawh.beta3.application.game.model.gamemanager.IModel;
import de.hawh.beta3.application.game.view.Screen.IViewImpl;
import de.hawh.beta3.application.stub.callee.IControllerCallee;
import de.hawh.beta3.application.stub.callee.IModelCallee;
import de.hawh.beta3.application.stub.callee.IModelViewCallee;
import de.hawh.beta3.application.stub.callee.IRemoteObject;
import de.hawh.beta3.middleware.IMiddleware;
import de.hawh.beta3.middleware.Middleware;
import de.hawh.beta3.middleware.nameservice.CommunicationPoint;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.FileReader;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Properties;
import java.util.stream.Collectors;

public class GameOfTrons extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Config lesen
        System.err.println("### Config wird gelesen");
        FileReader fr = new FileReader("gameoftrons.properties");
        Properties p = new Properties();
        p.load(fr);
        boolean remote = Boolean.parseBoolean(p.getProperty("remote"));
        System.err.println("### Es wird " + (remote ? "remote" : "lokal") + " gespielt!");

        if (remote) {
            fr = new FileReader("nameserver.properties");
            p.load(fr);
            int port = (int) p.get("nameServerPort");
            InetAddress NSip = InetAddress.getByName((String) p.get("nameserverIP"));

            // Nameserver starten
            CommunicationPoint nameServer = new CommunicationPoint(port, 4);
            nameServer.startNameServer();

            // Middleware aufbauen/ starten
            IMiddleware middleware = Middleware.getInstance();
            Middleware middlewareInstance = (Middleware) middleware;
            middlewareInstance.initialize(port, NSip);

            // Callee Objekte erzeugen
            IRemoteObject modelCallee = new IModelCallee();
            IRemoteObject modelViewCallee = new IModelViewCallee();
            IRemoteObject controllerCallee = new IControllerCallee();

            // VPN Adresse ermitteln
            InetAddress[] ipAddrs = InetAddress.getAllByName(InetAddress.getLocalHost().getHostName()); //get all IPs
            InetAddress ipVPN = Arrays.stream(ipAddrs).filter(ip -> ip.getHostAddress().startsWith("10.242."))
                    .collect(Collectors.toList()).get(0);   //find ip of vpn with 10.242.*.*

            // RemoteObjects registrieren
            // IModel
            middleware.register(0, modelCallee, "join",
                    InetAddress.getByName(ipVPN.getHostAddress()), true);
            middleware.register(0, modelCallee, "cancelWait",
                    InetAddress.getByName(ipVPN.getHostAddress()), true);
            middleware.register(0, modelCallee, "startGame",
                    InetAddress.getByName(ipVPN.getHostAddress()), true);
            middleware.register(0, modelCallee, "changePlayerDirection",
                    InetAddress.getByName(ipVPN.getHostAddress()), true);
            // IModelView
            middleware.register(0, modelViewCallee, "updateNumPlayers",
                    InetAddress.getByName(ipVPN.getHostAddress()), false);
            middleware.register(0, modelViewCallee, "updatePlayer",
                    InetAddress.getByName(ipVPN.getHostAddress()), false);
            middleware.register(0, modelViewCallee, "informUser",
                    InetAddress.getByName(ipVPN.getHostAddress()), false);
            // Controller
            middleware.register(0, controllerCallee, "setCurrentState",
                    InetAddress.getByName(ipVPN.getHostAddress()), false);
            middleware.register(0, controllerCallee, "handleInputPlayerCount",
                    InetAddress.getByName(ipVPN.getHostAddress()), false);
            middleware.register(0, controllerCallee, "handleWaitingButtonClick",
                    InetAddress.getByName(ipVPN.getHostAddress()), false);
            middleware.register(0, controllerCallee, "handleDirectionKeyboardInput",
                    InetAddress.getByName(ipVPN.getHostAddress()), false);
            middleware.register(0, controllerCallee, "notifyCountdownOver",
                    InetAddress.getByName(ipVPN.getHostAddress()), false);
            middleware.register(0, controllerCallee, "endGame",
                    InetAddress.getByName(ipVPN.getHostAddress()), false);
        }

        // MVC initialisieren
        IModel model = GameManager.getInstance();
        IContext controller = Context.getInstance();
        IViewImpl view = IViewImpl.getInstance();


        // Stage bauen
        stage.setWidth(javafx.stage.Screen.getPrimary().getBounds().getWidth() / 1.5);
        stage.setHeight(javafx.stage.Screen.getPrimary().getBounds().getHeight() / 1.5);

        view.showScreen("start");

        // configure and show stage
        stage.setTitle("Game of Trons - Not so Light BiCycles");
        stage.setScene(view.getScreen().getScene());
        stage.show();
    }
}
