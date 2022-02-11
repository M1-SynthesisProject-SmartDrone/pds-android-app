package Networkdata;

import com.google.gson.Gson;

import communicationAPI.Request;

public class TakeOffDrone {

    // --- Variables ---

    private boolean takeOff;

    // --- Methods ---

    public TakeOffDrone(boolean takeOff) {
        this.takeOff = takeOff;
    }

    public TakeOffDrone(Request request){
        if(request.getType() == RequestType.TAKEOFF){
            Gson gson = new Gson();
            String str = gson.toJson(request.getContent());

            TakeOffDrone bool = gson.fromJson(str, TakeOffDrone.class);
            takeOff = bool.isTakeOff();
        }
    }

    public boolean isTakeOff() {
        return takeOff;
    }

    public void setTakeOff(boolean takeOff) {
        this.takeOff = takeOff;
    }
}
