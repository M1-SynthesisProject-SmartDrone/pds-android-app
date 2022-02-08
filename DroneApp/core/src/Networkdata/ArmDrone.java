package Networkdata;

import com.google.gson.Gson;

import communicationAPI.Request;

public class ArmDrone {

    // --- Variables ---

    private boolean isArmed;

    // --- Methods ---


    public ArmDrone(boolean isArmed) {
        this.isArmed = isArmed;
    }

    public ArmDrone(Request request){
        if(request.getType() == RequestType.ARM){
            Gson gson = new Gson();
            String str = gson.toJson(request.getContent());

            ArmDrone bool = gson.fromJson(str, ArmDrone.class);
            isArmed = bool.isArmed();
        }
    }

    public boolean isArmed() {
        return isArmed;
    }

    public void setArmed(boolean armed) {
        isArmed = armed;
    }

}
