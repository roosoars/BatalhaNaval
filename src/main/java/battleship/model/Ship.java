package battleship.model;

import java.util.ArrayList;
import java.util.List;

public class Ship {
    private final String name;
    private final int length;
    private final boolean vertical;
    private final Coordinate start;
    private final List<Coordinate> positions = new ArrayList<>();
    private final List<Coordinate> hits = new ArrayList<>();

    public Ship(String name, Coordinate start, int length, boolean vertical) {
        this.name = name;
        this.start = start;
        this.length = length;
        this.vertical = vertical;
        for (int i = 0; i < length; i++) {
            if (vertical)
                positions.add(new Coordinate(start.getRow() + i, start.getCol()));
            else
                positions.add(new Coordinate(start.getRow(), start.getCol() + i));
        }
    }

    public String getName() { return name; }
    public boolean occupies(Coordinate c) { return positions.contains(c); }
    public boolean isHitAt(Coordinate c) { return hits.contains(c); }

    public boolean registerHit(Coordinate c) {
        if (occupies(c) && !hits.contains(c)) {
            hits.add(c);
            return true;
        }
        return false;
    }

    public boolean isSunk() { return hits.size() == length; }
    public List<Coordinate> getPositions() { return positions; }
}