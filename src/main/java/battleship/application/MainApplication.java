package battleship.application;

import static spark.Spark.*;
import java.io.FileInputStream;

import battleship.application.RouteConfig;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

public class MainApplication {
    public static void main(String[] args) throws Exception {
        FileInputStream serviceAccount = new FileInputStream("src/main/resources/serviceAccountKey.json");
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();
        FirebaseApp.initializeApp(options);

        port(8080);
        staticFiles.location("/public");
        RouteConfig.configureRoutes();
    }
}
