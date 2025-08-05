package battleship.application;

import static spark.Spark.*;
import battleship.application.RouteConfig;

public class MainApplication {
    public static void main(String[] args) {
        port(8080);
        staticFiles.location("/public");
        RouteConfig.configureRoutes();
    }
}