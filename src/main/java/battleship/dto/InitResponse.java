package battleship.dto;

public class InitResponse {
    private int size;
    public InitResponse() {}
    public InitResponse(int size) { this.size = size; }
    public int getSize() { return size; }
    public void setSize(int size) { this.size = size; }
}