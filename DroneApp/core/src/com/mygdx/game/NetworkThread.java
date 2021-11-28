package com.mygdx.game;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class NetworkThread extends Thread{
    DroneNetwork client;
    DroneControl controller;
    volatile boolean running;
    boolean isPrepared = false;
    String ip;
    int port;

    public NetworkThread(String ip, String port){
        isPrepared = true;
        this.ip = ip;
        this.port = Integer.parseInt(port);
    }
    
    @Override
    public void run() {
        if(isPrepared) {
            try {
                //client = new DroneNetwork("10.77.46.39", 6969);
//            client = new DroneNetwork("12.0.2.2", 5554);
                client = new DroneNetwork(ip, port);
                controller = controller.getInstance();
                running = true;
            } catch (UnknownHostException e) {
                System.out.println("UNKNOWNEXCEPTION ------------------------------------------");
                e.printStackTrace();
            } catch (SocketException e) {
                System.out.println("SOCKETEXCEPTION -------------------------------------------");
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            while (running) {
                try {
                    client.sendData(controller);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                client.endConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopThread(){
        running = false;
    }
}
