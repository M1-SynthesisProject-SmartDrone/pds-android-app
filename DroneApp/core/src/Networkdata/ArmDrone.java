package Networkdata;

import com.google.gson.Gson;

import communicationAPI.Request;

public class ArmDrone {

    // --- Variables ---

    private boolean armDrone;

    // --- Methods ---


    public ArmDrone(boolean armed) {
        this.armDrone = armed;
    }

    public ArmDrone(Request request){
        if(request.getType() == RequestType.ARM){
            Gson gson = new Gson();
            String str = gson.toJson(request.getContent());

            ArmDrone bool = gson.fromJson(str, ArmDrone.class);
            armDrone = bool.isArmDrone();
        }
    }

    public boolean isArmDrone() {
        return armDrone;
    }

    public void setArmDrone(boolean armDrone) {
        this.armDrone = armDrone;
    }

}
