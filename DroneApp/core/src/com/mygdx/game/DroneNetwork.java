package com.mygdx.game;

import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import sun.awt.X11.XSystemTrayPeer;

public class DroneNetwork {

    // ----------------------
    // Attributs
    // ----------------------
    final static int taille = 1024;
    static byte[] buffer;

    private InetAddress adress;
    private int port;
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
    }

    /**
     * Send the data contained in the droneControl class
     *
     * @param data
     * @throws IOException
     */
    public void sendData(DroneControl data) throws IOException {
        buffer = data.getJson().getBytes();
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
        buffer = "{ArmDrone : True}".getBytes();
        dataSent = new DatagramPacket(buffer, buffer.length, adress, port);
        socket.setBroadcast(true);
        socket.send(dataSent);
        System.out.println(" --- Asked for Drone Arming ... ");

        // Receiving the answer of the server
        boolean returnValue = true;
        DatagramPacket response = new DatagramPacket(buffer, buffer.length);
        socket.receive(response);
        String received = new String( response.getData(), 0, response.getLength());

        if(!received.contentEquals("{ArmDrone : True}")){
            returnValue= false;
        }

        return returnValue;
    }

    public void disarmDrone() throws IOException {
        // Send the request for Drone Disarmament
        buffer = "{ArmDrone : False}".getBytes();
        dataSent = new DatagramPacket(buffer, buffer.length, adress, port);
        socket.setBroadcast(true);
        socket.send(dataSent);
        System.out.println(" --- Asked for Drone Disarming ... ");

    }

    /**
     * End connection with the server
     */
    public void endConnection() throws IOException {
        socket.close();
    }
}
