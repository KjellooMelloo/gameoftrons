package de.hawh.beta3.application.game.controller;

import de.hawh.beta3.application.game.view.IControllerView;
import org.w3c.dom.ranges.Range;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Controller {

    IControllerView controllerView;
    StateMachine stateMachine;
    ArrayList<Object> configParameters;

    public Controller() {
    }

    private class StateMachine {
        Config config;

        public int handleInputPlayerCount(){
            return 0;
        }
    }

    private class Config {

    }


    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket(4321);

        while (true){
            // get their responses!

            System.out.println(socket.getRemoteSocketAddress());
            System.out.println(socket.getLocalSocketAddress());
            byte[] buf = new byte[1000];
            DatagramPacket recv = new DatagramPacket(buf, buf.length);
            socket.receive(recv);
            System.out.println(Arrays.toString(recv.getData()));
        }

    }

}
