package battleship.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Testes unitários para a classe Board
public class BoardTest {
    @Test
    public void testPlaceShipSuccess() {
        Board board = new Board(10);
        Ship ship = new Ship(3);
        ship.setStart(new Coordinate(0, 0));
        ship.setOrientation(false); // horizontal
        // Deve conseguir posicionar o navio sem sobreposição
        assertTrue(board.place(ship));
    }

    @Test
    public void testPlaceShipOutOfBounds() {
        Board board = new Board(5);
        Ship ship = new Ship(4);
        ship.setStart(new Coordinate(4, 3));
        ship.setOrientation(false); // horizontal
        // Não deve permitir posicionar fora do tabuleiro
        assertFalse(board.place(ship));
    }

    @Test
    public void testPlaceShipOverlap() {
        Board board = new Board(10);
        Ship ship1 = new Ship(3);
        ship1.setStart(new Coordinate(0, 0));
        ship1.setOrientation(false);
        Ship ship2 = new Ship(3);
        ship2.setStart(new Coordinate(0, 1));
        ship2.setOrientation(false);
        board.place(ship1);
        // Não deve permitir sobreposição de navios
        assertFalse(board.place(ship2));
    }

    @Test
    public void testAttackHitAndMiss() {
        Board board = new Board(5);
        Ship ship = new Ship(2);
        ship.setStart(new Coordinate(1, 1));
        ship.setOrientation(false);
        board.place(ship);
        // Deve acertar o navio
        AttackResult hit = board.attack(1, 1);
        assertTrue(hit.isHit());
        // Deve errar fora do navio
        AttackResult miss = board.attack(0, 0);
        assertFalse(miss.isHit());
    }

    @Test
    public void testGameOver() {
        Board board = new Board(3);
        Ship ship = new Ship(1);
        ship.setStart(new Coordinate(0, 0));
        ship.setOrientation(false);
        board.place(ship);
        // Após afundar todos os navios, gameOver deve ser true
        AttackResult result = board.attack(0, 0);
        assertTrue(result.isGameOver());
    }
}

