package networkManager;

import com.mygdx.game.DroneControl;
import com.mygdx.game.DroneNetwork;

import java.io.IOException;

/**
 * Class that control all communication coming from and to the app
 */
public class NetworkManager {

    // --- variables ---

    SenderThread sender;
    DroneNetwork client;
    DroneControl drone;

    // --- Methods ---

    public NetworkManager(String ip, int port) throws IOException {
        client = new DroneNetwork(ip, port);
        sender = new SenderThread(client);
        drone = DroneControl.getInstance();
        armDrone();
    }

    public void takeOffDrone(){
        drone.setTakeOff(true);
        try {
            client.takeOffDrone();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void landDrone(){
        drone.setTakeOff(false);
        try {
            client.landDrone();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void armDrone(){
        drone.armDrone();
        try {
            client.armDrone();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disarmDrone(){
        drone.disarmDrone();
        try {
            client.disarmDrone();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startSendingThread(){
        sender.start();
    }

    public void endSendingThread(){
        sender.stopThread();
    }

    public void disconnect(){
        endSendingThread();
        try {
            client.endConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
