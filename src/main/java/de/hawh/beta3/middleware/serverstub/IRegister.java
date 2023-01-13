package de.hawh.beta3.middleware.serverstub;

import de.hawh.beta3.application.stub.callee.IRemoteObject;

import java.net.InetAddress;

public interface IRegister {

    /**
     * Zum registrieren eines <code>IRemoteObject</code> im Nameserver mit seiner <code>interfaceID</code> und
     * <code>methodName</code>, sowie IP-Adresse <code>ipAddr</code> und Port <code>port</code>
     *
     * @param interfaceID   id des remote objects
     * @param remoteObject  Referenz des remote objects
     * @param methodName    zu registrierende Methode
     * @param ipAddr        IP-Adresse für remote-Ansprechbarkeit
     * @param port          Port für remote-Ansprechbarkeit
     */
    void register(int interfaceID, IRemoteObject remoteObject, String methodName, InetAddress ipAddr, int port);
}
