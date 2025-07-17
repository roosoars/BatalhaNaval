package com.batalhanaval.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.Firestore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @Value("${firebase.config.path}")
    private String firebaseConfigPath;

    @Bean
    public FirebaseApp firebaseApp() throws Exception {
        InputStream serviceAccount =
                this.getClass().getClassLoader()
                        .getResourceAsStream(firebaseConfigPath.replace("classpath:", ""));

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp app = FirebaseApp.initializeApp(options);
        System.out.println("[DEBUG] FirebaseApp initialized: " + app.getName());
        return app;
    }

    @Bean
    public FirebaseAuth firebaseAuth(FirebaseApp app) {
        FirebaseAuth auth = FirebaseAuth.getInstance(app);
        System.out.println("[DEBUG] FirebaseAuth bean created");
        return auth;
    }

    @Bean
    public Firestore firestore() {
        return FirestoreClient.getFirestore();
    }
}
