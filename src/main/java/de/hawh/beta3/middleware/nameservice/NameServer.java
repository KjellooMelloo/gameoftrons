package de.hawh.beta3.middleware.nameservice;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class NameServer implements INameServer, INameResolver {

    private Map<String[], Set<String>> cache = new HashMap<>();
    private static NameServer instance = new NameServer();

    private NameServer() {

    }

    public static NameServer getInstance() {
        return instance;
    }
    /**
     * Es soll nachgeschaut werden, ob es einen Eintrag mit <code>interfaceID</code> und <code>methodName</code>
     * im Nameserver gibt und diesen gegebenenfalls zurückliefern
     *
     * @param interfaceID id des Interfaces, das nachgeschaut werden soll
     * @param methodName  Methodenname, der nachgeschaut werden soll
     * @return String[] mit ipAddr an 0 und Port an 1
     */
    @Override
    public String[] lookup(int interfaceID, String methodName) {
        return new String[0];
    }

    /**
     * Ein remote object mit dem Schlüssel <code>interfaceID</code> und <code>methodName</code>
     * und dem Wert <code>ipAddr</code> und <code>port</code> soll im Nameserver eingetragen werden
     *
     * @param interfaceID id des Interfaces, das eingetragen werden soll
     * @param methodName  Methodenname, der eingetragen werden soll
     * @param ipAddr      IP-Adresse des remote objects
     * @param port        Port des remote objects
     */
    @Override
    public void bind(int interfaceID, String methodName, InetAddress ipAddr, int port) {

    }

    private boolean checkInterfaceInTable(String interfaceID, String methodName) {
        return cache.containsKey(new String[]{interfaceID, methodName});
    }
}
