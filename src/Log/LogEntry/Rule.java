package Log.LogEntry;

public class Rule {

    private String triggeredRule;
    private int numOfRequests;

    public Rule (String rule, int numOfRe) {
        this.triggeredRule = rule;
        this.numOfRequests = numOfRe;
    }

    public String getTriggeredRule() {
        return this.triggeredRule;
    }

    public int getNumOfRequests() {
        return this.numOfRequests;
    }
}
