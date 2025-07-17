package com.batalhanaval;

import com.batalhanaval.model.Ship;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShipPlacementServiceTest {
    private ShipPlacementService service;

    @BeforeEach
    void setUp() {
        service = new ShipPlacementService();
    }

    @Test
    void testNonOverlappingShips() {
        Ship s1 = new Ship(0, 0, 3, true, "cruiser");
        Ship s2 = new Ship(0, 1, 3, true, "cruiser");
        boolean valid = service.validateShipPlacement(List.of(s1, s2));
        assertTrue(valid, "Navios em linhas diferentes não devem sobrepor");
    }

    @Test
    void testOverlappingShipsSameRow() {
        Ship s1 = new Ship(0, 0, 4, true, "battleship");
        Ship s2 = new Ship(2, 0, 3, true, "cruiser"); // sobrepõe nas células (2,0),(3,0)
        boolean valid = service.validateShipPlacement(List.of(s1, s2));
        assertFalse(valid, "Navios na mesma linha com segment overlap devem ser inválidos");
    }

    @Test
    void testOverlappingShipsSameColumn() {
        Ship s1 = new Ship(5, 5, 3, false, "submarine");
        Ship s2 = new Ship(5, 6, 3, false, "submarine"); // sobrepõe em (5,6),(5,7)
        boolean valid = service.validateShipPlacement(List.of(s1, s2));
        assertFalse(valid, "Navios na mesma coluna com segment overlap devem ser inválidos");
    }

    @Test
    void testEdgeNonOverlapping() {
        Ship s1 = new Ship(9, 9, 1, true, "destroyer");
        Ship s2 = new Ship(0, 0, 1, true, "destroyer");
        assertTrue(service.validateShipPlacement(List.of(s1, s2)), "Navios distantes não devem sobrepor");
    }
}
