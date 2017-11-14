package videoupload.model;

import java.util.List;

public class Response {

private List<String> response;
    
    public Response(List<String> message){
        this.response = message;
    }

    public List<String> getResponse() {
        return response;
    }

    public void setResponse(List<String> response) {
        this.response = response;
    }
    
}
