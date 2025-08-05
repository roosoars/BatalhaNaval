package battleship.util;

import com.google.gson.Gson;
import spark.Request;

public class JsonUtil {
    private static final Gson gson = new Gson();

    public static <T> T fromJson(Request req, Class<T> clazz) {
        return gson.fromJson(req.body(), clazz);
    }

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }
}