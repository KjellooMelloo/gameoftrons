package de.hawh.beta3.middleware;

import de.hawh.beta3.application.stub.callee.IRemoteObject;
import de.hawh.beta3.middleware.clientstub.IRemoteInvocation;
import de.hawh.beta3.middleware.clientstub.Marshaler;
import de.hawh.beta3.middleware.clientstub.SenderImpl;
import de.hawh.beta3.middleware.serverstub.IRegister;
import de.hawh.beta3.middleware.serverstub.Receiver;
import de.hawh.beta3.middleware.serverstub.Unmarshaler;

import java.net.InetAddress;
import java.util.UUID;

/**
 * Middleware als Facade-Klasse für den AppStub.
 */
public class Middleware implements IMiddleware {

    private static Middleware mw = new Middleware();

    private IRemoteInvocation clientStub;// = new Marshaler.getInstance();
    private IRegister serverStub;   //= new Unmarshaler.getInstance();

    private Receiver receiver;

    private Middleware() {}

    public static Middleware getInstance() {
        return mw;
    }

    public void initialize(int port, InetAddress NSIp){
        clientStub = new Marshaler(new SenderImpl(port), port, NSIp);
        serverStub = new Unmarshaler(NSIp,port);
        receiver = new Receiver(port, (Unmarshaler) serverStub);
       // receiver.run();

    }

    public void startReceiver(){
        receiver.run();
    }

    /**
     * Eine remote Methode <code>methodName</code> vom Interface mit id <code>interfaceID</code>
     * soll über die Middleware mit Parametern <code>params</code> aufgerufen werden
     *
     * @param interfaceID id des Interfaces
     * @param methodName  Name der remote Methode
     * @param params      Parameter des Methodenaufrufs
     */
    @Override
    /**public void invoke(int interfaceID, String methodName, Object[] params) {
        clientStub.invoke(interfaceID, methodName, params);
    }**/
    public void invoke(UUID interfaceID, String methodName, Object[] params) {
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
     * @param isSingleton         Flag, ob sich nur eine instanz dieser Schnittstelle registriert werden darf
     */
    @Override
    /**public void register(int interfaceID, IRemoteObject remoteObject, String methodName, InetAddress ipAddr, boolean isSingleton) {
        serverStub.register(interfaceID, remoteObject, methodName, ipAddr, isSingleton);
    }**/
    public void register(UUID interfaceID, IRemoteObject remoteObject, String methodName, InetAddress ipAddr, boolean isSingleton) {
        serverStub.register(interfaceID, remoteObject, methodName, ipAddr, isSingleton);
    }
}
