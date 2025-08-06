package battleship.controller;

import spark.Request;
import spark.Response;
import battleship.dto.HistoryRequest;
import battleship.service.HistoryService;
import battleship.service.impl.HistoryServiceImpl;
import battleship.util.JsonUtil;
import java.util.UUID;
import java.util.Map;

public class HistoryController {
    private final HistoryService service = new HistoryServiceImpl();

    public Object addHistory(Request req, Response res) {
        String uid = req.attribute("uid");

        HistoryRequest dto = JsonUtil.fromJson(req, HistoryRequest.class);

        dto.setId(UUID.randomUUID().toString());
        dto.setTimestamp(System.currentTimeMillis());

        service.addHistory(uid, dto);

        res.status(204);
        return "";
    }

    public Object getHistory(Request req, Response res) {
        String uid = req.attribute("uid");
        res.type("application/json");
        return JsonUtil.toJson(service.getHistory(uid));
    }
}