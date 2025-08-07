package battleship.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CoordinateTest {
    @Test
    public void testEqualsAndHashCode() {
        Coordinate c1 = new Coordinate(1, 2);
        Coordinate c2 = new Coordinate(1, 2);
        Coordinate c3 = new Coordinate(2, 1);
        assertEquals(c1, c2, "Coordenadas iguais devem ser iguais");
        assertEquals(c1.hashCode(), c2.hashCode(), "HashCode deve ser igual para objetos iguais");
        assertNotEquals(c1, c3, "Coordenadas diferentes não devem ser iguais");
    }

    @Test
    public void testGetters() {
        Coordinate c = new Coordinate(3, 4);
        assertEquals(3, c.getRow());
        assertEquals(4, c.getCol());
    }
}

