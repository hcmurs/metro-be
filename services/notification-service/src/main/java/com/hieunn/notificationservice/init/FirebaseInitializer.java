/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Notification-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.hieunn.notificationservice.init;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.InputStream;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FirebaseInitializer {

  @PostConstruct
  public void init() {
    try {
      InputStream serviceAccount =
          getClass().getClassLoader().getResourceAsStream("firebase-service-account.json");

      assert serviceAccount != null;
      FirebaseOptions options =
          FirebaseOptions.builder()
              .setCredentials(GoogleCredentials.fromStream(serviceAccount))
              .build();

      if (FirebaseApp.getApps().isEmpty()) {
        FirebaseApp.initializeApp(options);
        log.info("FirebaseApp initialized successfully");
      }
    } catch (Exception e) {
      throw new RuntimeException(
          """
                                           Firebase service account file not found, check src/main/resources/firebase-service-account.json
                                           """,
          e);
    }
  }
}
