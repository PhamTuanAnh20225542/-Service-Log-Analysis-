package Log.LogEntry;

public class StatusCode {
    private int statusCode;
    private int numberOfRequests;

    public StatusCode(int statusCode, int requests){
        this.statusCode = statusCode;
        this.numberOfRequests = requests;
    }

    public int getStatusCode(){
        return this.statusCode;
    }

    public int getNumberOfRequests(){
        return this.numberOfRequests;
    }

    public void setStatusCode(int statusCode){
        this.statusCode = statusCode;
    }

    public void setNumberOfRequests(int requests){
        this.numberOfRequests = requests;
    }
}
