package battleship.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ShipFactoryTest {
    @Test
    public void testCreateShip() {
        ShipFactory factory = new ShipFactory();
        Ship ship = factory.create(4);
        assertNotNull(ship, "A fábrica deve criar um navio");
        // Verifica se o navio criado tem o tamanho correto
        ship.setStart(new Coordinate(0,0));
        ship.setOrientation(false);
        assertEquals(4, ship.getCoordinates().size());
    }
}

