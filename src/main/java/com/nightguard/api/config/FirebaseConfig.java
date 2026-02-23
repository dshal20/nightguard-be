package com.nightguard.api.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;

@Configuration
public class FirebaseConfig {

  @Bean
  public FirebaseApp firebaseApp() throws IOException {
    if (FirebaseApp.getApps().isEmpty()) {
      GoogleCredentials credentials = GoogleCredentials.fromStream(
          new ClassPathResource("serviceAccountKey.json").getInputStream());

      FirebaseOptions options = FirebaseOptions.builder()
          .setCredentials(credentials)
          .build();

      return FirebaseApp.initializeApp(options);
    }
    return FirebaseApp.getInstance();
  }

  @Bean
  public FirebaseAuth firebaseAuth() throws IOException {
    return FirebaseAuth.getInstance(firebaseApp());
  }
}