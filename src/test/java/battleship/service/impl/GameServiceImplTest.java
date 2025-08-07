package battleship.service.impl;

import battleship.dto.*;
import battleship.exception.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameServiceImplTest {
    private GameServiceImpl service;

    @BeforeEach
    public void setup() {
        service = new GameServiceImpl();
        // Inicializa o jogo com tabuleiro 10x10 para evitar loop infinito
        service.initGame(new InitRequest(10));
    }

    @Test
    public void testInitGame() {
        InitResponse resp = service.initGame(new InitRequest(7));
        assertEquals(7, resp.getSize());
    }

    @Test
    public void testPlacePlayerShipSuccess() {
        PlaceShipRequest req = new PlaceShipRequest(0, 0, 2, false);
        assertDoesNotThrow(() -> service.placePlayerShip(req));
    }

    @Test
    public void testPlacePlayerShipInvalid() {
        PlaceShipRequest req = new PlaceShipRequest(10, 10, 2, false);
        assertThrows(BadRequestException.class, () -> service.placePlayerShip(req));
    }

    @Test
    public void testPlayerAttackAndComputerAttack() {
        // Ataca uma posição válida
        AttackResponse resp = service.playerAttack(new AttackRequest(0, 0));
        assertNotNull(resp);
        // Computador ataca
        AttackResponse compResp = service.computerAttack();
        assertNotNull(compResp);
    }
}
