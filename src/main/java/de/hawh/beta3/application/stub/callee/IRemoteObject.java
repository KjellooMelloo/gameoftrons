package de.hawh.beta3.application.stub.callee;

import java.util.UUID;

public interface IRemoteObject {
    /**
     * Eine im Voraus registrierte Methode mit dem Namen <code>methodName</code> und den Parametern <code>args</code>
     * soll aufgerufen werden
     *
     * @param methodName registrierter Methodenname
     * @param args       Parameter des Aufrufs
     */
    void call(String methodName, Object[] args);

    int getId();
    UUID getUUID();
}
