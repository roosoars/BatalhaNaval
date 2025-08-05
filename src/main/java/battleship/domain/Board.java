package battleship.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    private final int size;
    private final List<Ship> ships = new ArrayList<>();
    private final List<Coordinate> attacks = new ArrayList<>();
    private final Random random = new Random();

    public Board(int size) {
        this.size = size;
    }

    public boolean place(Ship ship) {
        for (Coordinate c : ship.getCoordinates()) {
            if (c.getRow() < 0 || c.getRow() >= size || c.getCol() < 0 || c.getCol() >= size)
                return false;
        }
        for (Ship s : ships) {
            for (Coordinate c : ship.getCoordinates()) {
                if (s.occupies(c)) return false;
            }
        }
        ships.add(ship);
        return true;
    }

    public void placeRandom(Ship ship) {
        boolean placed = false;
        while (!placed) {
            int row = random.nextInt(size);
            int col = random.nextInt(size);
            ship.setStart(new Coordinate(row, col));
            ship.setOrientation(random.nextBoolean());
            placed = place(ship);
        }
    }

    public AttackResult attack(int row, int col) {
        Coordinate coord = new Coordinate(row, col);
        if (attacks.contains(coord)) return new AttackResult(false, false, null, row, col);
        attacks.add(coord);
        for (Ship ship : ships) {
            if (ship.occupies(coord)) {
                ship.registerHit(coord);
                boolean sunk = ship.isSunk();
                boolean gameOver = ships.stream().allMatch(Ship::isSunk);
                String sunkName = sunk ? "Ship of length " + ship.getCoordinates().size() : null;
                return new AttackResult(true, gameOver, sunkName, row, col);
            }
        }
        return new AttackResult(false, false, null, row, col);
    }

    public AttackResult randomAttack() {
        List<Coordinate> options = new ArrayList<>();
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                Coordinate coord = new Coordinate(r, c);
                if (!attacks.contains(coord)) options.add(coord);
            }
        }
        Coordinate choice = options.get(random.nextInt(options.size()));
        return attack(choice.getRow(), choice.getCol());
    }
}