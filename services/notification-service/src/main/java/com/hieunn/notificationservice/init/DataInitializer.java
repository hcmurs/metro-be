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

import com.github.javafaker.Faker;
import com.hieunn.notificationservice.domain.notifications.NotificationEntity;
import com.hieunn.notificationservice.domain.notifications.NotificationEntity.NotificationColor;
import com.hieunn.notificationservice.domain.notifications.NotificationEntity.NotificationIcon;
import com.hieunn.notificationservice.domain.notifications.NotificationEntity.NotificationType;
import com.hieunn.notificationservice.domain.notifications.NotificationRepository;
import com.hieunn.notificationservice.domain.token.UserDeviceTokenEntity;
import com.hieunn.notificationservice.domain.token.UserDeviceTokenRepository;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile({"dev", "test"})
public class DataInitializer {

  private final NotificationRepository notificationRepository;
  private final UserDeviceTokenRepository userDeviceTokenRepository;
  private static final int NUM_NOTIFICATIONS = 100;

  private static final Random random = new Random();
  private static final Faker faker = new Faker(new Locale("vi"));

  @EventListener(ApplicationReadyEvent.class)
  public void initializeData() {

    var email = "hoangclw@gmail.com";

    if (notificationRepository.count() == 0) {
      List<NotificationEntity> notifications = new ArrayList<>();

      for (int i = 0; i < NUM_NOTIFICATIONS; i++) {
        NotificationType type =
            NotificationType.values()[random.nextInt(NotificationType.values().length)];
        NotificationIcon icon =
            NotificationIcon.values()[random.nextInt(NotificationIcon.values().length)];
        NotificationColor color =
            NotificationColor.values()[random.nextInt(NotificationColor.values().length)];

        NotificationEntity notification =
            NotificationEntity.builder()
                .title(faker.book().title())
                .description(faker.lorem().sentence(10))
                .type(type)
                .email(email)
                .iconName(icon)
                .iconColorHex(color)
                .read(random.nextBoolean())
                .build();

        // 🔥 Random time range: within the last 60 days
        long randomDaysAgo = random.nextInt(60); // 0–59 days ago
        long randomHoursAgo = random.nextInt(24);
        long randomMinutesAgo = random.nextInt(60);

        notification.setCreatedOn(
            ZonedDateTime.now()
                .minusDays(randomDaysAgo)
                .minusHours(randomHoursAgo)
                .minusMinutes(randomMinutesAgo));

        // sometimes modified later, sometimes not
        notification.setLastModifiedOn(notification.getCreatedOn().plusHours(random.nextInt(24)));

        notifications.add(notification);
      }

      notificationRepository.saveAll(notifications);
      System.out.println(
          "✅ Inserted " + NUM_NOTIFICATIONS + " fake notifications with time variance");
    }

    if (userDeviceTokenRepository.count() > 0) return;

    var userDeviceTokenList =
        List.of(
            UserDeviceTokenEntity.builder()
                .email(email)
                .fcmToken("123")
                .deviceId("device-1")
                .deviceName("iPhone 12")
                .platform("iOS")
                .build(),
            UserDeviceTokenEntity.builder()
                .email(email)
                .fcmToken("345")
                .deviceId("device-1")
                .deviceName("iPhone 12")
                .platform("iOS")
                .build(),
            UserDeviceTokenEntity.builder()
                .email(email)
                .fcmToken("6576")
                .deviceId("device-1")
                .deviceName("iPhone 12")
                .platform("iOS")
                .build());
    userDeviceTokenRepository.saveAll(userDeviceTokenList);
  }
}
