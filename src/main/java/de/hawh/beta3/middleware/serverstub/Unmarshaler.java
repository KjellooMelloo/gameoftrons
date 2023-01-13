package de.hawh.beta3.middleware.serverstub;

import de.hawh.beta3.application.stub.callee.IRemoteObject;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class Unmarshaler implements IRegister, IReceiver {

    private Map<Integer, IRemoteObject> registeredRemoteObjects = new HashMap<>();

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

    }

    /**
     * <code>message</code> wird zur Verarbeitung weitergegeben
     *
     * @param message Empfangene Nachricht
     */
    @Override
    public void receive(DatagramPacket message) {
        unmarshal(message.getData());
    }

    /**
     * Methode erzeugt ein JSON-Objekt mit den Parametern, um es dem Nameserver als register-request zu schicken
     *
     * @param interfaceID id des Interfaces
     * @param methodName  Name der remote Methode
     * @param ipAddr      IP-Adresse des remote objects
     * @param port        Port des remote objects
     * @return JSON-Objekt als byte[] bereit zum Versenden
     */
    private byte[] serializeNS(int interfaceID, String methodName, String ipAddr, int port) {
        return null;
    }

    /**
     * Methode entpackt die Antwort des Nameservers und gibt ein String[] mit IP-Adresse und Port zurück
     *
     * @param message Antwort des Nameservers
     * @return int
     */
    private int deserializeNS(byte[] message) {
        return -1;
    }

    /**
     * Wandelt die <code>message</code> in ein JSON-Objekt um und ruft <code>callRemoteObjectInterface</code> auf
     *
     * @param message Empfangene Nachricht zum Umwandeln
     */
    private void unmarshal(byte[] message) {

    }

    /**
     * Entpackt Daten aus dem JSON-Objekt <code>json</code> und sucht das <code>IRemoteObject</code>
     * in <code>registeredRemoteObjects</code>. Ruft mit <code>call</code> das remote object mit den Werten
     * aus <code>json</code> auf
     */
    private void callRemoteObjectInterface(/*JSON json*/) {

    }
}
