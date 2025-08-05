package battleship.service;

import battleship.dto.*;

public interface GameService {
    InitResponse initGame(InitRequest request);
    void placePlayerShip(PlaceShipRequest request);
    AttackResponse playerAttack(AttackRequest request);
    AttackResponse computerAttack();
}