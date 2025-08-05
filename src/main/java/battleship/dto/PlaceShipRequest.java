package battleship.dto;

public class PlaceShipRequest {
    private int row;
    private int col;
    private int length;
    private boolean vertical;

    public PlaceShipRequest() {}
    public PlaceShipRequest(int row, int col, int length, boolean vertical) {
        this.row = row;
        this.col = col;
        this.length = length;
        this.vertical = vertical;
    }
    public int getRow() { return row; }
    public void setRow(int row) { this.row = row; }
    public int getCol() { return col; }
    public void setCol(int col) { this.col = col; }
    public int getLength() { return length; }
    public void setLength(int length) { this.length = length; }
    public boolean isVertical() { return vertical; }
    public void setVertical(boolean vertical) { this.vertical = vertical; }
}