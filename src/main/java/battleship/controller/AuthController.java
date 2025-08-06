package battleship.controller;

import spark.Request;
import spark.Response;
import battleship.dto.SignupRequest;
import battleship.util.JsonUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import java.util.Map;

public class AuthController {
    public Object signup(Request req, Response res) {
        SignupRequest dto = JsonUtil.fromJson(req, SignupRequest.class);
        try {
            CreateRequest r = new CreateRequest()
                    .setEmail(dto.getEmail())
                    .setPassword(dto.getPassword())
                    .setDisplayName(dto.getName());
            UserRecord user = FirebaseAuth.getInstance().createUser(r);
            res.status(201);
            return JsonUtil.toJson(Map.of("uid", user.getUid()));
        } catch (Exception e) {
            res.status(400);
            return JsonUtil.toJson(Map.of("error", e.getMessage()));
        }
    }
}
