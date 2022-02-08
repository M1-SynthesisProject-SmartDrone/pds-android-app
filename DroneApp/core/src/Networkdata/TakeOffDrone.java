package Networkdata;

import com.google.gson.Gson;

import communicationAPI.Request;

public class TakeOffDrone {

    // --- Variables ---

    private boolean isTakeoff;

    // --- Methods ---

    public TakeOffDrone(boolean isTakeoff) {
        this.isTakeoff = isTakeoff;
    }

    public TakeOffDrone(Request request){
        if(request.getType() == RequestType.TAKEOFF){
            Gson gson = new Gson();
            String str = gson.toJson(request.getContent());

            TakeOffDrone bool = gson.fromJson(str, TakeOffDrone.class);
            isTakeoff = bool.isTakeoff();
        }
    }

    public boolean isTakeoff() {
        return isTakeoff;
    }

    public void setTakeoff(boolean takeoff) {
        isTakeoff = takeoff;
    }
}
