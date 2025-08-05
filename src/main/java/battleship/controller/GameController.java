package battleship.controller;

import spark.Request;
import spark.Response;
import battleship.service.GameService;
import battleship.service.impl.GameServiceImpl;
import battleship.dto.*;
import battleship.util.JsonUtil;

public class GameController {
    private static final int DEFAULT_SIZE = 10;
    private final GameService gameService = new GameServiceImpl();

    /**
     * Inicializa o jogo lendo o parâmetro de query 'size'.
     * Usa DEFAULT_SIZE quando size não for informado.
     */
    public Object init(Request req, Response res) {
        String sizeParam = req.queryParams("size");
        int size = DEFAULT_SIZE;
        if (sizeParam != null) {
            try {
                size = Integer.parseInt(sizeParam);
            } catch (NumberFormatException e) {
                res.status(400);
                return "Parâmetro 'size' inválido";
            }
        }
        InitResponse response = gameService.initGame(new InitRequest(size));
        res.type("application/json");
        return JsonUtil.toJson(response);
    }

    public Object placeShip(Request req, Response res) {
        // Continua desserializando via JSON do corpo da requisição
        PlaceShipRequest dto = JsonUtil.fromJson(req, PlaceShipRequest.class);
        gameService.placePlayerShip(dto);
        res.status(204);
        return "";
    }

    public Object playerAttack(Request req, Response res) {
        AttackRequest dto = JsonUtil.fromJson(req, AttackRequest.class);
        AttackResponse response = gameService.playerAttack(dto);
        res.type("application/json");
        return JsonUtil.toJson(response);
    }

    public Object computerAttack(Request req, Response res) {
        AttackResponse response = gameService.computerAttack();
        res.type("application/json");
        return JsonUtil.toJson(response);
    }
}
