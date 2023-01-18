package de.hawh.beta3.middleware.clientstub;

import java.util.UUID;

public interface IRemoteInvocation {

    /**
     * Eine remote Methode <code>methodName</code> vom Interface mit id <code>interfaceID</code>
     * soll über die Middleware mit Parametern <code>params</code> aufgerufen werden
     *
     * @param interfaceID id des Interfaces
     * @param methodName  Name der remote Methode
     * @param params      Parameter des Methodenaufrufs
     */
    //void invoke(int interfaceID, String methodName, Object[] params);

    void invoke(UUID interfaceID, String methodName, Object[] params);
}
