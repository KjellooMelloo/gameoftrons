package de.hawh.beta3.middleware.nameservice;

import de.hawh.beta3.application.stub.callee.IModelViewCallee;
import de.hawh.beta3.middleware.serverstub.Unmarshaler;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Semaphore;

public class CommunicationPoint {

    private ServerSocket socket;
    private final int port;
    private Semaphore sem;
    private INameServer nameServer = new NameServer();

    public CommunicationPoint(int port, int maxThreads) {
        this.port = port;
        this.sem = new Semaphore(maxThreads);
    }

    /**
     * Startet den CommunicationPoint, der dann auf <code>port</code> hört und eingehende Nachrichten an Worker abgibt
     */
    public void startNameServer() {
        try {
            socket = new ServerSocket(port);
            Socket connSocket;

            while (true) {
                sem.acquire();
                connSocket = socket.accept();
                (new CommunicationPointWorker(connSocket, nameServer, this)).run();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Semaphore getSem() {
        return sem;
    }

    private static class CommunicationPointWorker implements Runnable {

        private Socket socket;
        private CommunicationPoint boss;
        private DataInputStream dis;
        private DataOutputStream dos;
        private INameServer nameServer;

        public CommunicationPointWorker(Socket socket, INameServer nameServer, CommunicationPoint boss) {
            this.socket = socket;
            this.nameServer = nameServer;
            this.boss = boss;
        }

        /**
         * When an object implementing interface {@code Runnable} is used
         * to create a thread, starting the thread causes the object's
         * {@code run} method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method {@code run} is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            try {
                dis = new DataInputStream(socket.getInputStream());
                dos = new DataOutputStream(socket.getOutputStream());
                int msgLength = dis.readInt();
                byte[] buf = new byte[msgLength];
                dis.readFully(buf, 0, buf.length);

                JSONObject json = deserialize(buf);
                JSONArray argsArray = json.getJSONArray("args");
                JSONObject argsJSONOBj = (JSONObject)argsArray.get(0);

                if (json.getInt("method") == 0) {   //lookup
                    doLookup(argsJSONOBj);
                } else {    //bind
                    doBind(argsJSONOBj);
                }

                socket.close();
                dis.close();
                dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                boss.getSem().release();
            }
        }

        /**
         * Wandelt eine Nachricht in ein <code>JSONObject</code> um
         *
         * @param message Nachricht zu deserialisieren
         * @return <code>JSONObject</code> aus <code>message</code>
         */
        private JSONObject deserialize(byte[] message) {
            return new JSONObject(new String(message, StandardCharsets.UTF_8));
        }

        /**
         * Wandelt ein <code>JSONObject</code> in eine Nachricht um
         *
         * @param json JSON-Objekt zu serialisieren
         * @return <code>message</code> aus <code>JSONObject</code>
         */
        private byte[] serialize(JSONObject json) {
            return json.toString().getBytes(StandardCharsets.UTF_8);
        }

        /**
         * Ruft lookup vom <code>NameServer</code> auf und schickt die Antwort an den Client
         *
         * @param args Argumente für den Lookup-Aufruf aus dem JSON-Objekt
         * @throws IOException Wenn eine IOException vorkommt
         */
        private void doLookup(JSONObject args) throws IOException {
            String[] ipAddrs = nameServer.lookup(args.getInt("ifaceID"), args.getString("methodName")).toArray(String[]::new);
            JSONObject res = new JSONObject();

            for (int i = 0; i < ipAddrs.length; i++) {
                res.put("ipAddr" + (i+1), ipAddrs[i]);
            }
            byte[] resByteMsg = serialize(res);
            dos.writeInt(resByteMsg.length);
            dos.write(resByteMsg);
        }

        /**
         * Ruft bind vom <code>NameServer</code> auf
         *
         * @param args Argumente für den Bind-Aufruf aus dem JSON-Objekt
         * @throws IOException Wenn eine IOException vorkommt
         */
        private void doBind(JSONObject args) throws IOException {
            nameServer.bind(args.getInt("ifaceID"), args.getString("methodName"), args.getString("ipAddr"), args.getBoolean("isSingleton")  );
        }
    }

}
