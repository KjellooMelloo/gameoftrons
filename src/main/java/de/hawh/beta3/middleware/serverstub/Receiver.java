package de.hawh.beta3.middleware.serverstub;

import javafx.application.Platform;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Receiver implements Runnable {

    private DatagramSocket socket;
    private int port;
    private final Unmarshaler unmarshaler;

    public Receiver(int port, Unmarshaler unmarshaler){
        this.port = port;
        this.unmarshaler = unmarshaler;
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
            socket = new DatagramSocket(port);
            while (true) {
                System.out.println("Receiver running");
                // Größtes Paket durch Netzwerk hat 164 Bytes. Nächste Zweierpotenz ist ausreichend
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                Thread rec = new Thread(new ReceiverThread(packet));
                Platform.runLater(rec);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    class ReceiverThread implements Runnable {

        DatagramPacket packet;

        public ReceiverThread(DatagramPacket packet){
            this.packet = packet;
        }
        public void run(){
            unmarshaler.receive(packet);
        }
    }

}
