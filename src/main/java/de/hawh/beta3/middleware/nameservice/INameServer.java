package de.hawh.beta3.middleware.nameservice;

import java.net.InetAddress;

public interface INameServer {

    /**
     * Ein remote object mit dem Schlüssel <code>interfaceID</code> und <code>methodName</code>
     * und dem Wert <code>ipAddr</code> und <code>port</code> soll im Nameserver eingetragen werden
     *
     * @param interfaceID   id des Interfaces, das eingetragen werden soll
     * @param methodName    Methodenname, der eingetragen werden soll
     * @param ipAddr        IP-Adresse des remote objects
     * @param port          Port des remote objects
     */
    void bind(int interfaceID, String methodName, InetAddress ipAddr, int port);
}
