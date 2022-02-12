package communicationAPI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Networkdata.RequestType;

public class RequestImpl implements Request{

    // --- Variables ---

    private String type;
    private Object content;


    // --- Methods ---

    public RequestImpl(String type, Object content) {
        this.type = type;
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    @Override
    public String getRequest() {
        String request = "{ \"type\" : \"" + type +"\" , \"content\" :";
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();

        request+= gson.toJson(content);
        request+="}";
        return request;
    }

    public  Request receiveRequest(String json){
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Request req = gson.fromJson(json, RequestImpl.class);
        return req;
    }





}
