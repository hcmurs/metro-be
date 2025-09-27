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

import com.hieunn.notificationservice.domain.notifications.NotificationEntity;
import com.hieunn.notificationservice.domain.notifications.NotificationEntity.NotificationColor;
import com.hieunn.notificationservice.domain.notifications.NotificationEntity.NotificationIcon;
import com.hieunn.notificationservice.domain.notifications.NotificationEntity.NotificationType;
import com.hieunn.notificationservice.domain.notifications.NotificationRepository;
import com.hieunn.notificationservice.domain.token.UserDeviceTokenEntity;
import com.hieunn.notificationservice.domain.token.UserDeviceTokenRepository;
import java.util.List;
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

  @EventListener(ApplicationReadyEvent.class)
  public void initializeData() {

    var email = "hoangclw@gmail.com";

    if (notificationRepository.count() > 0) return;

    var notificationList =
        List.of(
            NotificationEntity.builder()
                .title("Notification 1")
                .description("This is the first notification")
                .type(NotificationType.WARNING)
                .email(email)
                .iconName(NotificationIcon.ERROR)
                .iconColorHex(NotificationColor.RED)
                .build(),
            NotificationEntity.builder()
                .title("Notification 2")
                .description("This is the second notification")
                .type(NotificationType.INFO)
                .email(email)
                .iconName(NotificationIcon.MESSAGE)
                .iconColorHex(NotificationColor.BLUE)
                .build());

    notificationRepository.saveAll(notificationList);

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
