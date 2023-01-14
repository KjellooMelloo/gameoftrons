package de.hawh.beta3.middleware.clientstub;

import javafx.util.Pair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Marshaler implements IRemoteInvocation {

    private final Map<Pair<String, String>, Set<String>> cache = new HashMap<>();
    private ISender sender;



    /**
     * Eine remote Methode <code>methodName</code> vom Interface mit id <code>interfaceID</code>
     * soll über die Middleware mit Parametern <code>params</code> aufgerufen werden
     *
     * @param interfaceID id des Interfaces
     * @param methodName  Name der remote Methode
     * @param params      Parameter des Methodenaufrufs
     */
    @Override
    public void invoke(int interfaceID, String methodName, Object[] params) {
        byte[] msg = marshal(interfaceID, methodName, params);
        String[] ipAddresses = cacheOrLookup(interfaceID, methodName);
        sender.send(ipAddresses, msg);
    }

    /**
     * Methode erzeugt JSON-Objekt aus den Parametern und wandelt es dann in ein byte[] um
     *
     * @param interfaceID id des Interfaces
     * @param methodName  Name der remote Methode
     * @param params      Parameter des Methodenaufrufs
     * @return JSON-Objekt als byte[] bereit zum Versenden
     */
    public byte[] marshal(int interfaceID, String methodName, Object[] params) {
        JSONObject jsonObj = new JSONObject();
        JSONArray paramsJSON = new JSONArray();

        jsonObj.put("interfaceID", interfaceID);
        jsonObj.put("methodName", methodName);


        if (params.length > 0) {
            JSONObject methodParam = new JSONObject();
            for (int i = 0; i < params.length; i++) {
                methodParam.put("type" + (i + 1), params[i].getClass().getSimpleName());
                methodParam.put("val" + (i + 1), params[i]);
            }
            paramsJSON.put(methodParam);
        }
        jsonObj.put("args", paramsJSON);

        // Convert to Byte-Array


        byte[] msg = jsonObj.toString().getBytes(StandardCharsets.UTF_8);
        return msg;

    }

    /**
     * Schaut erst im <code>cache</code> nach, ob es einen Eintrag mit den Parametern gibt und andernfalls wird beim
     * Nameserver angefragt mithilfe von <code>serializeNS</code> und <code>deserializeNS</code>.
     * Gibt dann zugehörige IP und Port zurück
     *
     * @param interfaceID id des Interfaces
     * @param methodName  Name der remote Methode
     * @return String[] mit ipAddr an 0 und Port an 1
     */
    private String[] cacheOrLookup(int interfaceID, String methodName) {
        // Test
        cache.put(new Pair<>("0", "updatePlayer"),
                new HashSet<String>(){{
                    add("123.122.233.34");
                    add("123.444.567.4");
        }});
        //Test


        Set<String> ipSet;
        Pair<String, String> key = new Pair<>(String.valueOf(interfaceID), methodName);


        if (cache.containsKey(key)) {
            System.out.println("Contains key: " + key);
            ipSet = cache.get(key);
            System.out.println("Set of IPs: " + ipSet);
            String[] res = new String[ipSet.size()];
            int i = 0;
            for(String ip : ipSet){
                res[i] = ip;
                i++;
            }
            return res;
        } else {

            byte[] lookupMsg = serializeNS(interfaceID, methodName);
            //send NS

            //byte[] response = ...;
            //res = deserializeNS(response);
            //cache(key, res);
            return null;
        }

    }



    /**
     * Methode erzeugt ein JSON-Objekt mit den Parametern, um es dem Nameserver als lookup-request zu schicken
     *
     * @param interfaceID id des Interfaces
     * @param methodName  Name der remote Methode
     * @return JSON-Objekt als byte[] bereit zum Versenden
     */
    private byte[] serializeNS(int interfaceID, String methodName) {

        JSONObject jsonMsg = new JSONObject();
        JSONArray argsJSONArray = new JSONArray();
        JSONObject jsonObjectArgs = new JSONObject();

        jsonMsg.put("method",0);
        jsonObjectArgs.put("ifaceID", interfaceID);
        jsonObjectArgs.put("methodName", methodName);

        argsJSONArray.put(jsonObjectArgs);

        jsonMsg.put("args", argsJSONArray);
        // Convert to Byte-Array


        byte[] msg = jsonMsg.toString().getBytes(StandardCharsets.UTF_8);
        return msg;


    }

    /**
     * Methode entpackt die Antwort des Nameservers und gibt ein String[] mit IP-Adresse und Port zurück
     *
     * @param message Antwort des Nameservers
     * @return String[] mit ipAddr an 0 und Port an 1
     */
    private String[] deserializeNS(byte[] message) {
        return null;
    }

    /**
     * Speichert <code>ipAndPort</code> zu <code>interfaceID</code> und <code>methodName</code> im <code>cache</code>
     *
     * @param interfaceID id des Interfaces
     * @param methodName  Name der remote Methode
     * @param ipAndPort   String[] mit ipAddr an 0 und Port an 1
     */
    private void cache(int interfaceID, String methodName, String[] ipAndPort) {
        String[] key = new String[]{String.valueOf(interfaceID), methodName};
        //cache.put(key, ipAndPort);
    }


}
