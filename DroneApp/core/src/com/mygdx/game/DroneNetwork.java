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
     * End connection with the server
     */
    public void endConnection() throws IOException {
        socket.close();
    }
}
