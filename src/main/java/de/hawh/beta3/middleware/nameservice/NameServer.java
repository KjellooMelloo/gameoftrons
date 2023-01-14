package de.hawh.beta3.middleware.nameservice;

import java.net.InetAddress;
import java.util.*;

class NameServer implements INameServer, INameResolver {

    private Map<String[], Set<String>> cache = new HashMap<>(); //TODO Set<String> oder Set<String[]>
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
        String[] res;

        if (checkInterfaceInTable(interfaceID, methodName)) {
            String[] key = new String[]{String.valueOf(interfaceID), methodName};
            res = cache.get(key).toArray(String[]::new);
        } else {
            res = new String[]{""};
        }

        return res;
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
        String[] key = new String[]{String.valueOf(interfaceID), methodName};
        String ipAndPort = ipAddr.toString() + ":" + port;

        if (!checkInterfaceInTable(interfaceID, methodName)) {
            cache.put(key, new HashSet<>());
        }
        cache.get(key).add(ipAndPort);
    }

    private boolean checkInterfaceInTable(int interfaceID, String methodName) {
        return cache.containsKey(new String[]{String.valueOf(interfaceID), methodName});
    }
}
