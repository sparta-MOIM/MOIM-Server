//package com.sparta.moim.notificationservice.infrastruct.config;
//
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.FirebaseOptions;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//
//public class FirebaseInitializer {
//    public static void initialize() throws IOException {
//        FileInputStream serviceAccount = new FileInputStream("src/main/resources/firebase-service-key.json");
//
//        FirebaseOptions options = FirebaseOptions.builder()
//                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                .build();
//
//        if (FirebaseApp.getApps().isEmpty()) {
//            FirebaseApp.initializeApp(options);
//        }
//    }
//}
