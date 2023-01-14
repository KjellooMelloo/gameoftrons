package de.hawh.beta3.middleware.nameservice;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.Semaphore;

public class CommunicationPoint {

    private ServerSocket socket;
    private final int port;
    private Semaphore sem;

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
                (new CommunicationPointWorker(connSocket, this)).run();
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
}

class CommunicationPointWorker implements Runnable {

    private Socket socket;
    private CommunicationPoint boss;
    private DataInputStream dis;
    private DataOutputStream dos;
    private NameServer nameServer = NameServer.getInstance();

    public CommunicationPointWorker(Socket socket, CommunicationPoint boss) {
        this.socket = socket;
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
            byte[] buf = new byte[1000];
            dis.read(buf);

            JSONObject json = deserialize(buf);
            JSONArray args = json.getJSONArray("args");

            if (json.getInt("method") == 0) {   //lookup
                doLookup(args);
            } else {    //bind
                doBind(args);
            }

            socket.close();
            dis.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            boss.getSem().release();
        }
    }

    private JSONObject deserialize(byte[] message) {
        return new JSONObject();
    }

    //TODO weg?
    private byte[] serialize(JSONObject json) {
        return null;
    }

    /**
     * Ruft lookup vom <code>NameServer</code> auf und schickt die Antwort an den Client
     *
     * @param args  Argumente für den Lookup-Aufruf aus dem JSON-Objekt
     *
     * @throws IOException Wenn eine IOException vorkommt
     */
    private void doLookup(JSONArray args) throws IOException {
        String[] ipAndPort = nameServer.lookup(args.getInt(0), args.getString(1));
        dos.writeBytes(Arrays.toString(ipAndPort));
        dos.close();
    }

    /**
     * Ruft bind vom <code>NameServer</code> auf
     *
     * @param args  Argumente für den Bind-Aufruf aus dem JSON-Objekt
     *
     * @throws IOException Wenn eine IOException vorkommt
     */
    private void doBind(JSONArray args) throws IOException {
        nameServer.bind(args.getInt(0), args.getString(1),
                InetAddress.getByName(args.getString(2)), args.getInt(3));
        dos.close();
    }
}
