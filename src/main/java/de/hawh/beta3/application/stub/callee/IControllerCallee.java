package de.hawh.beta3.application.stub.callee;

import de.hawh.beta3.application.game.controller.statemachine.Context;
import de.hawh.beta3.application.game.controller.statemachine.IContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class IControllerCallee implements IRemoteObject {
    private IContext iContext = Context.getInstance();
    private Method method;
    private int id = 2;

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
            for (Method m : iContext.getClass().getDeclaredMethods()) {
                if (m.getName().equals(methodName)) {
                    m.invoke(iContext, args);
                    break;
                }
            }
        } catch (IllegalAccessException e) {

            e.printStackTrace();
        } catch (InvocationTargetException e) {
            System.err.println(e.getTargetException());
        }
    }

    public int getId() {
        return id;
    }
}
