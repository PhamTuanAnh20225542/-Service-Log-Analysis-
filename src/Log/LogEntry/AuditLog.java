package Log.LogEntry;

public class AuditLog {
    private final String type = "Modsecurity Audit Log";
    private String timestamp;
    private String remoteAddress;
    private String request;
    private String triggeredRule;

    public AuditLog(String timestamp, String remoteAddress, String request, String triggeredRule) {
        this.timestamp = timestamp;
        this.remoteAddress = remoteAddress;
        this.request = request;
        this.triggeredRule = triggeredRule;
    }

    public String getType(){
        return type;
    }

    public String getTimestamp(){
        return this.timestamp;
    }

    public String getRemoteAddress(){
        return this.remoteAddress;
    }

    public String getRequest(){
        return this.request;
    }

    public String getTriggeredRule(){
        return this.triggeredRule;
    }

    public String toString() {
        return this.remoteAddress + " " + this.timestamp + " " + this.request + " " + this.triggeredRule;
    }
}
