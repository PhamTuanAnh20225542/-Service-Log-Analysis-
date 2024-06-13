package Log.LogEntry;

public class AccessLog {
    private  String type;
    private  String IP;
    private  String remoteIdent;
    private  String remoteUser;
    private  String timestamp;
    private  String userAgent;
    private  String requestUrl;
    private  int statusCode;
    private  int bytesSent;
    private  String referer;

    public AccessLog(String ip, String remoteId, String remoteUser, String time, String userAgent, String request, 
                    int statusCode, int bytesSent, String referer){
        this.type = "Apache Access Log";
        this.IP = ip;
        this.remoteIdent = remoteId;
        this.remoteUser = remoteUser;
        this.timestamp = time;
        this.userAgent = userAgent;
        this.requestUrl = request;
        this.statusCode = statusCode;
        this.bytesSent = bytesSent;
        this.referer = referer;
    }

    public String getType(){
        return type;
    }
    
    public String getIP() {
        return IP;
    }

    public String getTimestamp(){
        return timestamp;
    }
    
    public String getRemoteIdent() {
        return remoteIdent;
    }

    public String getRemoteUser() {
        return remoteUser;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public int getBytesSent() {
        return bytesSent;
    }

    public String getReferer() {
        return referer;
    }
}

