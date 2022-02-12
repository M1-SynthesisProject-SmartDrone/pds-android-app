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
    final static int BUFFER_SIZE = 1024 * 8;

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
        socket = new DatagramSocket(port);
        socket.setSoTimeout(10000);
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
        byte[] buffer = data.getBytes();
        dataSent = new DatagramPacket(buffer, buffer.length, adress, port);
        socket.setBroadcast(true);
        socket.send(dataSent);
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
        byte[] buffer = req.getRequest().getBytes();
        dataSent = new DatagramPacket(buffer, buffer.length, adress, port);
        socket.setBroadcast(true);
        socket.send(dataSent);
        System.out.println(" --- Asked for Drone Arming ... ");

        // Receiving the answer of the server

        DatagramPacket response = receiveResponse();
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
        byte[] buffer = req.getRequest().getBytes();
        dataSent = new DatagramPacket(buffer, buffer.length, adress, port);
        socket.setBroadcast(true);
        socket.send(dataSent);

        System.out.println(" --- Asked for Drone Disarming ... ");

        DatagramPacket response = receiveResponse();
        String received = new String( response.getData(), 0, response.getLength());
        Request resp = req.receiveRequest(received);
        ArmDrone droneState = new ArmDrone(resp);
        if(droneState.isArmDrone()){
            drone.disarmDrone();
            return false;
        }
        return true;
    }

    private DatagramPacket receiveResponse() throws IOException {
        byte[] responseBuffer = new byte[BUFFER_SIZE];
        DatagramPacket response = new DatagramPacket(responseBuffer, responseBuffer.length);
        socket.receive(response);
        return response;
    }

    public boolean takeOffDrone() throws IOException {
        Request req = new RequestImpl(RequestType.TAKEOFF, new TakeOffDrone(true));
        byte[] buffer = req.getRequest().getBytes();
        dataSent = new DatagramPacket(buffer, buffer.length, adress, port);
        socket.setBroadcast(true);
        socket.send(dataSent);

        System.out.println(" --- Asked for Drone TakeOff ... ");

        DatagramPacket response = receiveResponse();
        String received = new String( response.getData(), 0, response.getLength());
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
        byte[] buffer = req.getRequest().getBytes();
        dataSent = new DatagramPacket(buffer, buffer.length, adress, port);
        socket.setBroadcast(true);
        socket.send(dataSent);

        System.out.println(" --- Asked for Drone TakeOff ... ");


        DatagramPacket response = receiveResponse();
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
