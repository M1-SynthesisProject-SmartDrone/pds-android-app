package networkManager;

import com.mygdx.game.DroneControl;
import com.mygdx.game.DroneNetwork;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import Networkdata.ControlDrone;
import Networkdata.RequestType;
import communicationAPI.Request;
import communicationAPI.RequestImpl;

public class SenderThread extends Thread{

    // --- Variables ---

    DroneControl drone;
    DroneNetwork client;
    Request request;
    ControlDrone control;
    boolean running;

    // --- Methods ---

    public SenderThread(DroneNetwork client){
        drone = DroneControl.getInstance();
        this.client = client;
        control = new ControlDrone();
    }

    @Override
    public void run(){
        System.out.println("Sender thread started");
        running = true;
        while (running) {
            if(drone.isDroneArmed() && drone.isTakeOff()) {
                // initialise drone communication
                control.updateData(drone.getFrontMove(), drone.getLateralMove(), drone.getMotorPercentage(), drone.getRotation());
                request = new RequestImpl(RequestType.MANUAL_CONTROL, control);


                // sending data continuously

                try {
                    client.sendData(request.getRequest());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }


    }

    public void stopThread(){
        running = false;
    }

}
