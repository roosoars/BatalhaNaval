package battleship.service.impl;

import battleship.service.GameService;
import battleship.domain.*;
import battleship.dto.*;
import battleship.exception.*;

public class GameServiceImpl implements GameService {
    private Board playerBoard;
    private Board computerBoard;
    private final ShipFactory shipFactory = new ShipFactory();

    @Override
    public InitResponse initGame(InitRequest request) {
        int size = request.getSize();
        this.playerBoard = new Board(size);
        this.computerBoard = new Board(size);
        for (int len : new int[]{2,3,3,4,5}) {
            Ship ship = shipFactory.create(len);
            computerBoard.placeRandom(ship);
        }
        return new InitResponse(size);
    }

    @Override
    public void placePlayerShip(PlaceShipRequest req) {
        Ship ship = shipFactory.create(req.getLength());
        ship.setOrientation(req.isVertical());
        ship.setStart(new Coordinate(req.getRow(), req.getCol()));
        if (!playerBoard.place(ship)) {
            throw new BadRequestException("Não foi possível posicionar o navio.");
        }
    }

    @Override
    public AttackResponse playerAttack(AttackRequest req) {
        AttackResult result = computerBoard.attack(req.getRow(), req.getCol());
        return new AttackResponse(
                result.isHit(),
                result.isGameOver(),
                result.getSunkName(),
                result.getRow(),
                result.getCol()
        );
    }

    @Override
    public AttackResponse computerAttack() {
        AttackResult result = playerBoard.randomAttack();
        return new AttackResponse(
                result.isHit(),
                result.isGameOver(),
                result.getSunkName(),
                result.getRow(),
                result.getCol()
        );
    }
}