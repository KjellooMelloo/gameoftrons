package de.hawh.beta3.middleware.clientstub;

import java.net.InetAddress;

interface ISender {

    /**
     * Verpackt die <code>message</code> in ein UDP-Datagramm und schickt das Paket an
     * <code>ipAddr</code> mit <code>port</code>
     *
     * @param ipAddr  IP-Zieladresse
     * @param port    Zielport
     * @param message Zu verschickende Nachricht
     */
    void send(String[] ipAddr, byte[] message);
}
