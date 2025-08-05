package battleship.dto;

public class InitRequest {
    private int size;
    public InitRequest() {}
    public InitRequest(int size) { this.size = size; }
    public int getSize() { return size; }
    public void setSize(int size) { this.size = size; }
}