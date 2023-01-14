package de.hawh.beta3.middleware.nameservice;

import javafx.util.Pair;

import java.net.InetAddress;
import java.util.*;

class NameServer implements INameServer {

    private Map<Pair<Integer, String>, Set<String>> cache = new HashMap<>();

    /**
     * Es soll nachgeschaut werden, ob es einen Eintrag mit <code>interfaceID</code> und <code>methodName</code>
     * im Nameserver gibt und diesen gegebenenfalls zurückliefern
     *
     * @param interfaceID id des Interfaces, das nachgeschaut werden soll
     * @param methodName  Methodenname, der nachgeschaut werden soll
     * @return String[] mit ipAddr an 0 und Port an 1
     */
    @Override
    public Set<String> lookup(int interfaceID, String methodName) {
        Set<String> res;

        if (checkInterfaceInTable(interfaceID, methodName)) {
            Pair<Integer, String> key = new Pair<>(interfaceID, methodName);
            res = cache.get(key);
        } else {
            res = new HashSet<>(){};
            res.add("");
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
     */
    @Override
    public void bind(int interfaceID, String methodName, InetAddress ipAddr) {
        Pair<Integer, String> key = new Pair<>(interfaceID, methodName);

        if (!checkInterfaceInTable(interfaceID, methodName)) {
            cache.put(key, new HashSet<>());
        }
        cache.get(key).add(ipAddr.toString());
    }

    /**
     * Prüft, ob es einen Eintrag mit <code>interfaceID</code> und <code>methodName</code> gibt
     *
     * @param interfaceID id des Interfaces
     * @param methodName  Methodenname
     * @return  true, wenn enthalten, false sonst
     */
    private boolean checkInterfaceInTable(int interfaceID, String methodName) {
        return cache.containsKey(new Pair<>(interfaceID, methodName));
    }
}
