package battleship.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Testes unitários para BadRequestException
public class BadRequestExceptionTest {
    @Test
    public void testExceptionMessage() {
        String msg = "Mensagem de erro";
        BadRequestException ex = new BadRequestException(msg);
        assertEquals(msg, ex.getMessage());
    }
}

