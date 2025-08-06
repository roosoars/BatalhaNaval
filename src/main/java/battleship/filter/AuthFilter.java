package battleship.filter;

import spark.Request;
import spark.Response;
import spark.Filter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

import static spark.Spark.halt;

public class AuthFilter {
    public static void verifyToken(Request req, Response res) throws Exception {
        String header = req.headers("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            halt(401, "Token ausente");
        }
        String idToken = header.substring(7);
        FirebaseToken decoded = FirebaseAuth.getInstance().verifyIdToken(idToken);
        req.attribute("uid", decoded.getUid());
    }
}
