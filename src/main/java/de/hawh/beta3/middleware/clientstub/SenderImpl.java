package de.hawh.beta3.middleware.clientstub;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SenderImpl implements ISender {

    private DatagramSocket socket;

    /**
     * Verpackt die <code>message</code> in ein UDP-Datagramm und schickt das Paket an
     * <code>ipAddr</code> mit <code>port</code>
     *
     * @param ipAddr  IP-Zieladresse
     * @param port    Zielport
     * @param message Zu verschickende Nachricht
     */
    @Override   //TODO an Threads abgeben?
    public void send(InetAddress ipAddr, int port, byte[] message) {
        try {
            socket = new DatagramSocket();
            DatagramPacket packet = new DatagramPacket(message, message.length, ipAddr, port);
            socket.send(packet);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
