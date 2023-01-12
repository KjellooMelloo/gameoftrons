package de.hawh.beta3.middleware;

import de.hawh.beta3.application.stub.callee.IRemoteObject;
import de.hawh.beta3.middleware.clientstub.IRemoteInvocation;
import de.hawh.beta3.middleware.serverstub.IRegister;

import java.net.InetAddress;

/**
 * Middleware als Facade-Klasse für den AppStub.
 */
public class Middleware implements IMiddleware {

    private IRemoteInvocation clientStub;   //= new Marshaler.getInstance();
    private IRegister serverStub;   //= new Unmarshaler.getInstance();

    /**
     * Eine remote Methode <code>methodName</code> vom Interface mit id <code>interfaceID</code>
     * soll über die Middleware mit Parametern <code>params</code> aufgerufen werden
     *
     * @param interfaceID id des Interfaces
     * @param methodName  Name der remote Methode
     * @param params      Parameter des Methodenaufrufs
     */
    @Override
    public void invoke(int interfaceID, String methodName, Object[] params) {
        clientStub.invoke(interfaceID, methodName, params);
    }

    /**
     * Zum registrieren eines <code>IRemoteObject</code> im Nameserver mit seiner <code>interfaceID</code> und
     * <code>methodName</code>, sowie IP-Adresse <code>ipAddr</code> und Port <code>port</code>
     *
     * @param interfaceID  id des remote objects
     * @param remoteObject Referenz des remote objects
     * @param methodName   zu registrierende Methode
     * @param ipAddr       IP-Adresse für remote-Ansprechbarkeit
     * @param port         Port für remote-Ansprechbarkeit
     */
    @Override
    public void register(int interfaceID, IRemoteObject remoteObject, String methodName, InetAddress ipAddr, int port) {
        serverStub.register(interfaceID, remoteObject, methodName, ipAddr, port);
    }
}
