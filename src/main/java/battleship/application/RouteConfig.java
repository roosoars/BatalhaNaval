package battleship.application;

import static spark.Spark.*;
import battleship.controller.GameController;

public class RouteConfig {
    public static void configureRoutes() {
        path("/api", () -> {
            GameController controller = new GameController();
            get("/init", controller::init);
            post("/place", controller::placeShip);
            post("/attack", controller::playerAttack);
            get("/attack/computer", controller::computerAttack);
        });
    }
}