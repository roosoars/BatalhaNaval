package battleship.dto;

public class HistoryRequest {
    private String id;
    private String result;
    private String opponentType;
    private long timestamp;

    public HistoryRequest() {}

    public HistoryRequest(String id, String result, String opponentType, long timestamp) {
        this.id = id;
        this.result = result;
        this.opponentType = opponentType;
        this.timestamp = timestamp;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }

    public String getOpponentType() { return opponentType; }
    public void setOpponentType(String opponentType) { this.opponentType = opponentType; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}
