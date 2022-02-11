package com.mygdx.game;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import Networkdata.ArmDrone;
import Networkdata.RequestType;
import Networkdata.TakeOffDrone;
import communicationAPI.Request;
import communicationAPI.RequestImpl;

public class DroneNetwork {

    // ----------------------
    // Attributs
    // ----------------------
    final static int taille = 1024;
    static byte[] buffer;

    private InetAddress adress;
    private int port;
    private DroneControl drone;
    private DatagramPacket dataSent;
    private DatagramSocket socket;

    // ----------------------
    // Methods
    // ----------------------

    /**
     * Start a connection with the server given in a parameter
     *
     * @param ip
     * @param port
     * @throws UnknownHostException
     * @throws SocketException
     */
    public DroneNetwork(String ip, int port) throws IOException {
        adress = InetAddress.getByName(ip);
        //adress = InetAddress.getLocalHost();
        this.port = port;
        socket = new DatagramSocket();
        socket.setSoTimeout(30000);
        System.out.println("Connection done !");
        drone = DroneControl.getInstance();
    }

    /**
     * Send the data contained in the droneControl class
     *
     * @param data
     * @throws IOException
     */
    public void sendData(String data) throws IOException {
        buffer = data.getBytes();
        dataSent = new DatagramPacket(buffer, buffer.length, adress, port);
        socket.setBroadcast(true);
        socket.send(dataSent);
        System.out.println("NETWORK SEND DATAS : " + adress);
    }

    /**
     * Arm the drone
     *
     * @return boolean to say if the drone has been correctly armed
     * @throws IOException
     */
    public boolean armDrone() throws IOException {
        // Send the request for Drone Armement
        Request req = new RequestImpl(RequestType.ARM, new ArmDrone(true));
        buffer = req.getRequest().getBytes();
        dataSent = new DatagramPacket(buffer, buffer.length, adress, port);
        socket.setBroadcast(true);
        socket.send(dataSent);
        System.out.println(" --- Asked for Drone Arming ... ");

        // Receiving the answer of the server

        DatagramPacket response = new DatagramPacket(buffer, buffer.length);
        socket.receive(response);
        String received = new String( response.getData(), 0, response.getLength());
        Request resp = req.receiveRequest(received);
        ArmDrone droneState = new ArmDrone(resp);
        if(!droneState.isArmDrone()){
            return false;
        }
        drone.armDrone();
        return true;
    }

    public boolean disarmDrone() throws IOException {
        // Send the request for Drone Disarmament
        Request req = new RequestImpl(RequestType.ARM, new ArmDrone(false));
        //buffer = "{ArmDrone : False}".getBytes();
        buffer = req.getRequest().getBytes();
        dataSent = new DatagramPacket(buffer, buffer.length, adress, port);
        socket.setBroadcast(true);
        socket.send(dataSent);

        System.out.println(" --- Asked for Drone Disarming ... ");

        DatagramPacket response = new DatagramPacket(buffer, buffer.length);
        socket.receive(response);
        String received = new String( response.getData(), 0, response.getLength());
        Request resp = req.receiveRequest(received);
        ArmDrone droneState = new ArmDrone(resp);
        if(droneState.isArmDrone()){
            drone.disarmDrone();
            return false;
        }
        return true;
    }

    public boolean takeOffDrone() throws IOException {
        Request req = new RequestImpl(RequestType.TAKEOFF, new TakeOffDrone(true));
        buffer = req.getRequest().getBytes();
        dataSent = new DatagramPacket(buffer, buffer.length, adress, port);
        socket.setBroadcast(true);
        socket.send(dataSent);

        System.out.println(" --- Asked for Drone TakeOff ... ");

        DatagramPacket response = new DatagramPacket(buffer, buffer.length);
        socket.receive(response);
        String received = new String( response.getData(), 0, response.getLength());
        System.out.println(received);
        Request resp = req.receiveRequest(received);
        TakeOffDrone droneState = new TakeOffDrone(resp);
        if(!droneState.isTakeOff()){
            return false;
        }
        drone.setTakeOff(true);
        return true;
    }

    public boolean landDrone() throws IOException {
        Request req = new RequestImpl(RequestType.TAKEOFF, new TakeOffDrone(false));
        buffer = req.getRequest().getBytes();
        dataSent = new DatagramPacket(buffer, buffer.length, adress, port);
        socket.setBroadcast(true);
        socket.send(dataSent);

        System.out.println(" --- Asked for Drone TakeOff ... ");

        DatagramPacket response = new DatagramPacket(buffer, buffer.length);
        socket.receive(response);
        String received = new String( response.getData(), 0, response.getLength());
        Request resp = req.receiveRequest(received);
        TakeOffDrone droneState = new TakeOffDrone(resp);
        if(droneState.isTakeOff()){
            return false;
        }
        drone.setTakeOff(false);
        return true;
    }

    /**
     * End connection with the server
     */
    public void endConnection() throws IOException {
        socket.close();
    }
}
