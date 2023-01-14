package de.hawh.beta3.middleware.nameservice;

import java.net.InetAddress;
import java.util.Set;

public interface INameServer {

    /**
     * Ein remote object mit dem Schlüssel <code>interfaceID</code> und <code>methodName</code>
     * und dem Wert <code>ipAddr</code> und <code>port</code> soll im Nameserver eingetragen werden
     *
     * @param interfaceID   id des Interfaces, das eingetragen werden soll
     * @param methodName    Methodenname, der eingetragen werden soll
     * @param ipAddr        IP-Adresse des remote objects
     */
    void bind(int interfaceID, String methodName, InetAddress ipAddr);

    /**
     * Es soll nachgeschaut werden, ob es einen Eintrag mit <code>interfaceID</code> und <code>methodName</code>
     * im Nameserver gibt und diesen gegebenenfalls zurückliefern
     *
     * @param interfaceID   id des Interfaces, das nachgeschaut werden soll
     * @param methodName    Methodenname, der nachgeschaut werden soll
     * @return String-Set mit IP-Adressen
     */
    Set<String> lookup(int interfaceID, String methodName);
}
