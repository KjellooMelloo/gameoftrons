package de.hawh.beta3.application.stub.callee;

import de.hawh.beta3.application.game.view.IModelView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class IModelViewCallee implements IRemoteObject {
    private IModelView modelView; //= new ModelViewImpl();
    private Method method;
    private int id = 1;

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
            Class<?>[] params = (Class<?>[]) Arrays.stream(args).map(Object::getClass).toArray();
            method = modelView.getClass().getMethod(methodName, params);
            method.invoke(modelView);
        } catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }
}
