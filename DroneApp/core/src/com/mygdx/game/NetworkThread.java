package com.mygdx.game;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import Networkdata.RequestType;
import communicationAPI.Request;
import communicationAPI.RequestImpl;

public class NetworkThread extends Thread{
    DroneNetwork client;
    DroneControl controller;
    volatile boolean running;
    Request request;
    boolean isPrepared = false;
    boolean isTakeOff;
    String ip;
    int port;


    public NetworkThread(String ip, String port){
        isPrepared = true;
        isTakeOff = false;
        this.ip = ip;
        this.port = Integer.parseInt(port);
    }

    /**
     * Ask to the server to arm the drone
     *
     * @return true if drone has been armed, false if not
     */
    public boolean armDrone() throws IOException {
        boolean isArmed = client.armDrone();
        if(isArmed){
            controller.armDrone();
            return true;
        }
        return false;
    }

    public void disarmDrone() throws IOException {
        client.disarmDrone() ;
        controller.disarmDrone();
    }

    public boolean takeOffDrone() throws IOException {
        boolean isTakeOff = client.takeOffDrone();
        if(isTakeOff){
            return true;
        }
        return false;
    }

    public boolean landDrone() throws IOException {
        boolean isTakeOff = client.landDrone();
        if(isTakeOff){
            return true;
        }
        return false;
    }

    @Override
    public void run() {
        if(isPrepared && isTakeOff) {
            try {
                client = new DroneNetwork(ip, port);
                controller = controller.getInstance();
                request = new RequestImpl(RequestType.MANUAL_CONTROL, controller);
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
            boolean isArmed;
            try {
               isArmed = armDrone();
            } catch (IOException e) {
                e.printStackTrace();
            }

            while (running) {
                try {
                    client.sendData(request.getRequest());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                disarmDrone();
            } catch (IOException e) {
                e.printStackTrace();
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
