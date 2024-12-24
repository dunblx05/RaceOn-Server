package com.parting.dippin.core.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Slf4j
@Profile("!test")
@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void init() throws IOException {
//        FileInputStream serviceAccount = new FileInputStream(
//            "src/main/resources/raceon-firebase-admin.json"
//        );
        InputStream serviceAccount = getClass()
                .getClassLoader()
                .getResourceAsStream("raceon-firebase-admin.json");

        assert serviceAccount != null;
        FirebaseOptions options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build();

        try {
            FirebaseApp.getInstance(FirebaseApp.DEFAULT_APP_NAME);
        } catch (IllegalStateException e) {
            FirebaseApp.initializeApp(options);
        }
    }
}
