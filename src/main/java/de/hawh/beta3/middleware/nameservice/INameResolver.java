package de.hawh.beta3.middleware.nameservice;

public interface INameResolver {

    /**
     * Es soll nachgeschaut werden, ob es einen Eintrag mit <code>interfaceID</code> und <code>methodName</code>
     * im Nameserver gibt und diesen gegebenenfalls zurückliefern
     *
     * @param interfaceID   id des Interfaces, das nachgeschaut werden soll
     * @param methodName    Methodenname, der nachgeschaut werden soll
     * @return String[] mit ipAddr an 0 und Port an 1
     */
    String[] lookup(int interfaceID, String methodName);
}
