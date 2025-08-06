package battleship.application;

import static spark.Spark.*;
import battleship.controller.*;
import battleship.filter.AuthFilter;

public class RouteConfig {
    public static void configureRoutes() {
        path("/api/auth", () -> {
            AuthController auth = new AuthController();
            post("/signup", auth::signup);
        });

        before("/api/history", AuthFilter::verifyToken);
        path("/api/history", () -> {
            HistoryController hist = new HistoryController();
            post("", hist::addHistory);
            get("",  hist::getHistory);
        });

        before("/api/game/*", AuthFilter::verifyToken);
        path("/api/game", () -> {
            GameController game = new GameController();
            get("/init",             game::init);
            post("/place",           game::placeShip);
            post("/attack",          game::playerAttack);
            get("/attack/computer",  game::computerAttack);
        });
    }
}