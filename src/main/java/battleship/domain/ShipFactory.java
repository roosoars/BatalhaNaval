package battleship.domain;

public class ShipFactory {
    public Ship create(int length) {
        return new Ship(length);
    }
}