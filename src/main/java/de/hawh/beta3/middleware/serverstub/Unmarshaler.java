package de.hawh.beta3.middleware.serverstub;

import de.hawh.beta3.application.stub.callee.IRemoteObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Unmarshaler implements IRegister, IReceiver {

    private final Map<Integer, IRemoteObject> registeredRemoteObjects = new HashMap<>() {
    };

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
        String json = new String(message, StandardCharsets.UTF_8);
        JSONObject byteMsgToJSON = new JSONObject(json);
        callRemoteObjectInterface(byteMsgToJSON);
    }

    /**
     * Entpackt Daten aus dem JSON-Objekt <code>json</code> und sucht das <code>IRemoteObject</code>
     * in <code>registeredRemoteObjects</code>. Ruft mit <code>call</code> das remote object mit den Werten
     * aus <code>json</code> auf
     */
    private void callRemoteObjectInterface(JSONObject jsonMsg) {

        // Get interfaceID and Mmethod name
        int interfaceID = jsonMsg.getInt("interfaceID");
        System.out.println("interfaceID: " + interfaceID);
        String methodName = jsonMsg.getString("methodName");
        System.out.println("methodName: " + methodName);

        // Extract parameters for method call
        JSONArray argsJSONArray = jsonMsg.getJSONArray("args");
        Object[] callParameters = new Object[0];

        // Check if there are any parameters for method call
        if (argsJSONArray.length() > 0) {
            JSONObject argsJSONObj = argsJSONArray.getJSONObject(0);
            callParameters = new Object[argsJSONObj.length()];
            String type, val;

            System.out.println("LEngth argsJSONOBJ: " + argsJSONObj.length());
            // Convert to expected typ
            for (int i = 0; i < argsJSONObj.length() / 2; i++) {
                type = argsJSONObj.getString("type" + (i + 1));
                System.out.println("type" + (i + 1) + ": " + argsJSONObj.getString("type" + (i + 1)));
                val = argsJSONObj.get("val" + (i + 1)).toString();
                System.out.println("Converted Val: " + convertValueToType(type, val));
                callParameters[i] = convertValueToType(type, val);
            }

            // call method
            IRemoteObject interfaceToBeCalled = registeredRemoteObjects.get(interfaceID);
            interfaceToBeCalled.call(methodName, callParameters);

        }


    }

    /**
     * Konvertiert den Aufrufparameter zum erwarteten Datentyp
     */

    private Object convertValueToType(String type, String val) {
        if (type.equalsIgnoreCase("int") || type.equalsIgnoreCase("Integer")) {
            return Integer.parseInt(val);
        }

        if (type.equalsIgnoreCase("string")) {
            return val;
        }

        if (type.equalsIgnoreCase("double")) {
            return Double.parseDouble(val);
        } else
            throw new RuntimeException("Type not recognized");
    }

}
