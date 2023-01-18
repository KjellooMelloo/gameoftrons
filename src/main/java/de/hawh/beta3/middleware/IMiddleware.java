package de.hawh.beta3.middleware;

import de.hawh.beta3.application.stub.callee.IRemoteObject;

import java.net.InetAddress;
import java.util.UUID;

public interface IMiddleware {

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

    /**
     * Zum registrieren eines <code>IRemoteObject</code> im Nameserver mit seiner <code>interfaceID</code> und
     * <code>methodName</code>, sowie IP-Adresse <code>ipAddr</code> und Port <code>port</code>
     *
     * @param interfaceID  id des remote objects
     * @param remoteObject Referenz des remote objects
     * @param methodName   zu registrierende Methode
     * @param ipAddr       IP-Adresse für remote-Ansprechbarkeit
     * @param isSingleton  Flag, ob sich nur eine instanz dieser Schnittstelle registriert werden darf
     */
    //void register(int interfaceID, IRemoteObject remoteObject, String methodName, InetAddress ipAddr, boolean isSingleton);
    void register(UUID interfaceID, IRemoteObject remoteObject, String methodName, InetAddress ipAddr, boolean isSingleton);
}
