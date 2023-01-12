package de.hawh.beta3.middleware.serverstub;

import java.net.DatagramPacket;

interface IReceiver {

    /**
     * <code>message</code> wird zur Verarbeitung weitergegeben
     *
     * @param message   Empfangene Nachricht
     */
    void receive(DatagramPacket message);
}
