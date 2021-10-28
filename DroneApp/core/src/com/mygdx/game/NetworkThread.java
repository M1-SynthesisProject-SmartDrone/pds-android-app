package com.mygdx.game;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class NetworkThread implements Runnable{
    DroneNetwork client;
    DroneControl controller;
    @Override
    public void run() {
        try {
            client = new DroneNetwork("10.77.46.39", 6969);
//            client = new DroneNetwork("12.0.2.2", 5554);
            controller = controller.getInstance();
        } catch (UnknownHostException e) {
            System.out.println("UNKNOWNEXCEPTION ------------------------------------------");
            e.printStackTrace();
        } catch (SocketException e) {
            System.out.println("SOCKETEXCEPTION -------------------------------------------");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true){
            try {
                client.sendData(controller);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
