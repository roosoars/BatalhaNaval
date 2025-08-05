package battleship.dto;

public class AttackResponse {
    private boolean hit;
    private boolean gameOver;
    private String sunk;
    private int row;
    private int col;

    public AttackResponse() {}
    public AttackResponse(boolean hit, boolean gameOver, String sunk, int row, int col) {
        this.hit = hit;
        this.gameOver = gameOver;
        this.sunk = sunk;
        this.row = row;
        this.col = col;
    }

    public boolean isHit() { return hit; }
    public void setHit(boolean hit) { this.hit = hit; }
    public boolean isGameOver() { return gameOver; }
    public void setGameOver(boolean gameOver) { this.gameOver = gameOver; }
    public String getSunk() { return sunk; }
    public void setSunk(String sunk) { this.sunk = sunk; }
    public int getRow() { return row; }
    public void setRow(int row) { this.row = row; }
    public int getCol() { return col; }
    public void setCol(int col) { this.col = col; }
}