package communicationAPI;

import Networkdata.ArmDrone;
import Networkdata.ControlDrone;
import Networkdata.RequestType;

public class test {

    public static void main(String[] args) {
        float num = (float) 0.4;
        RequestImpl p = new RequestImpl(RequestType.MANUAL_CONTROL, new ControlDrone(num, num, 0.4, 1));

        RequestImpl req = new RequestImpl(RequestType.MANUAL_CONTROL, p);
        System.out.println(p.getRequest());
        String str = req.getRequest();

        RequestImpl resp = (RequestImpl) req.receiveRequest(str);
        System.out.println(resp.getContent());

        Request armreq = new RequestImpl(RequestType.ARM, new ArmDrone(true));
        ArmDrone arm = new ArmDrone(armreq);
        System.out.println(arm.isArmDrone());
    }
}
