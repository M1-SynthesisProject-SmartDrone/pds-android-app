package communicationAPI;

public interface Request {

    public String getRequest();

    public Request receiveRequest(String json);

    public String getType();

    public Object getContent();



}
