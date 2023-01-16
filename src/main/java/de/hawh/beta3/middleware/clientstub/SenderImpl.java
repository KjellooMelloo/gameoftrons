package de.hawh.beta3.middleware.clientstub;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SenderImpl implements ISender {

    //private DatagramSocket socket;
    private int port;

    public SenderImpl(int port){
        this.port = port;

    }

    /**
     * Verpackt die <code>message</code> in ein UDP-Datagramm und schickt das Paket an
     * <code>ipAddr</code> mit <code>port</code>
     *
     * @param ipAddr  IP-Zieladresse
     * @param message Zu verschickende Nachricht
     */
    @Override
    public void send(String[] ipAddr, byte[] message) {

        Thread sender = new Thread(new SenderThread(ipAddr,message));
        sender.start();

    }

    class SenderThread implements Runnable {

        String[] ipAddr;
        byte[] message;

        public SenderThread(String[] ipAddr, byte[] message){
            this.ipAddr = ipAddr;
            this.message = message;
        }

        @Override
        public void run() {
            try {
                DatagramSocket socket = new DatagramSocket();
                for(int i=0; i<ipAddr.length;i++) {
                    InetAddress ip = InetAddress.getByName(ipAddr[i]);
                    DatagramPacket packet = new DatagramPacket(message, message.length, ip, port);
                    socket.send(packet);
                }
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }
}
