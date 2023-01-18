package de.hawh.beta3.application.stub.callee;

import de.hawh.beta3.application.game.view.IModelView;
import de.hawh.beta3.application.game.view.Screen.IViewImpl;
//import de.hawh.beta3.application.game.view.Screen.IModelViewImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.UUID;

public class IModelViewCallee implements IRemoteObject {
    private IModelView modelView = IViewImpl.getInstance();
    private Method method;
    private int id = 1;
    private UUID uuid = UUID.fromString("bb202cde-bdf0-49e0-94c2-c52f649ed67b");


    /**
     * Eine im Voraus registrierte Methode mit dem Namen <code>methodName</code> und den Parametern <code>args</code>
     * soll aufgerufen werden
     *
     * @param methodName registrierter Methodenname
     * @param args       Parameter des Aufrufs
     */
    @Override
    public void call(String methodName, Object[] args) {
        //TODO Übersetzung mit Adapter bei vereinbarten gemeinsamen Methodennamen
        try {
            for (Method m : modelView.getClass().getDeclaredMethods()) {
                if (m.getName().equals(methodName)) {
                    m.invoke(modelView, args);
                    break;
                }
            }
        } catch(IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            System.err.println(e.getTargetException());
        }
    }

    public int getId() {
        return id;
    }

    public UUID getUUID() {return uuid;}

}
