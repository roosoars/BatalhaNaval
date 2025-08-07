package battleship.domain;

import org.junit.jupiter.api.Test;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

// Testes unitários para a classe Ship
public class ShipTest {
    @Test
    public void testGetCoordinatesHorizontal() {
        Ship ship = new Ship(3);
        ship.setStart(new Coordinate(2, 2));
        ship.setOrientation(false); // horizontal
        Set<Coordinate> coords = ship.getCoordinates();
        assertTrue(coords.contains(new Coordinate(2,2)));
        assertTrue(coords.contains(new Coordinate(2,3)));
        assertTrue(coords.contains(new Coordinate(2,4)));
        assertEquals(3, coords.size());
    }

    @Test
    public void testGetCoordinatesVertical() {
        Ship ship = new Ship(2);
        ship.setStart(new Coordinate(1, 1));
        ship.setOrientation(true); // vertical
        Set<Coordinate> coords = ship.getCoordinates();
        assertTrue(coords.contains(new Coordinate(1,1)));
        assertTrue(coords.contains(new Coordinate(2,1)));
        assertEquals(2, coords.size());
    }

    @Test
    public void testOccupies() {
        Ship ship = new Ship(2);
        ship.setStart(new Coordinate(0, 0));
        ship.setOrientation(false);
        assertTrue(ship.occupies(new Coordinate(0,0)));
        assertTrue(ship.occupies(new Coordinate(0,1)));
        assertFalse(ship.occupies(new Coordinate(1,0)));
    }

    @Test
    public void testRegisterHitAndIsSunk() {
        Ship ship = new Ship(2);
        ship.setStart(new Coordinate(0, 0));
        ship.setOrientation(false);
        assertFalse(ship.isSunk());
        ship.registerHit(new Coordinate(0,0));
        assertFalse(ship.isSunk());
        ship.registerHit(new Coordinate(0,1));
        assertTrue(ship.isSunk());
    }
}

