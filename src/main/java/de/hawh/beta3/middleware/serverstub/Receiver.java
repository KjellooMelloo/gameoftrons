package de.hawh.beta3.middleware.serverstub;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Receiver implements Runnable {

    private DatagramSocket socket;
    private final IReceiver receiver = new Unmarshaler(); //= Unmarshaler.getInstance();

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
    @Override   //TODO brauchen noch port hierfür
    public void run() {
        Thread receiver = new Thread(new ReceiverThread());
        receiver.start();
    }

    class ReceiverThread implements Runnable {

        public void run(){
            try {
                socket = new DatagramSocket();  //+ port
                while (true) {
                    byte[] buffer = new byte[1000];
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);
                    receiver.receive(packet);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //public void setPort(int port) {}
}
