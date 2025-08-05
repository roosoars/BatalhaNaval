package battleship.domain;

import java.util.HashSet;
import java.util.Set;

public class Ship {
    private final int length;
    private boolean vertical;
    private Coordinate start;
    private final Set<Coordinate> hits = new HashSet<>();

    public Ship(int length) {
        this.length = length;
    }

    public void setOrientation(boolean vertical) { this.vertical = vertical; }
    public void setStart(Coordinate start) { this.start = start; }

    public Set<Coordinate> getCoordinates() {
        Set<Coordinate> coords = new HashSet<>();
        for (int i = 0; i < length; i++) {
            int r = vertical ? start.getRow() + i : start.getRow();
            int c = vertical ? start.getCol() : start.getCol() + i;
            coords.add(new Coordinate(r, c));
        }
        return coords;
    }

    public boolean occupies(Coordinate coord) { return getCoordinates().contains(coord); }
    public void registerHit(Coordinate coord) {
        if (occupies(coord)) hits.add(coord);
    }
    public boolean isSunk() { return hits.size() == length; }
}