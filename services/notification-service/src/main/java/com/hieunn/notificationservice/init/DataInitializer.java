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

  @EventListener(ApplicationReadyEvent.class)
  public void initializeData() {

    if (notificationRepository.count() > 0) return;

    var notificationList =
        List.of(
            NotificationEntity.builder()
                .title("Notification 1")
                .description("This is the first notification")
                .type(NotificationType.WARNING)
                .email("hoangclw@gmail.com")
                .iconName(NotificationIcon.ERROR)
                .iconColorHex(NotificationColor.RED)
                .build(),
            NotificationEntity.builder()
                .title("Notification 2")
                .description("This is the second notification")
                .type(NotificationType.INFO)
                .email("hoangclw@gmail.com")
                .iconName(NotificationIcon.MESSAGE)
                .iconColorHex(NotificationColor.BLUE)
                .build());

    notificationRepository.saveAll(notificationList);
  }
}
