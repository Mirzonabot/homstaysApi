package com.example.api_updated;

import com.google.firebase.FirebaseOptions;
import com.google.firebase.FirebaseApp;
import com.google.auth.oauth2.GoogleCredentials;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@SpringBootApplication
public class ApiUpdatedApplication {

    public static void main(String[] args) throws IOException {
        ClassLoader classLoader = HomestaybookingApplication.class.getClassLoader();
        File file = new File(classLoader.getResource("google-services.json").getFile());
        assert file.exists();
        FileInputStream serviceAccount =
                new FileInputStream(file.getAbsolutePath());

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://homestaybooking-f8308-default-rtdb.europe-west1.firebasedatabase.app")
                .build();
        FirebaseApp.initializeApp(options);
        SpringApplication.run(ApiUpdatedApplication.class, args);
    }

}
