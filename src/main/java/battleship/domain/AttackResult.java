package battleship.domain;

public class AttackResult {
    private final boolean hit;
    private final boolean gameOver;
    private final String sunkName;
    private final int row;
    private final int col;

    public AttackResult(boolean hit, boolean gameOver, String sunkName, int row, int col) {
        this.hit = hit;
        this.gameOver = gameOver;
        this.sunkName = sunkName;
        this.row = row;
        this.col = col;
    }

    public boolean isHit() { return hit; }
    public boolean isGameOver() { return gameOver; }
    public String getSunkName() { return sunkName; }
    public int getRow() { return row; }
    public int getCol() { return col; }
}