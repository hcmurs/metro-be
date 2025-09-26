/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Notification-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.hieunn.notificationservice.domain.notifications;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hieunn.notificationservice.domain.notifications.NotificationEntity.NotificationColor;
import com.hieunn.notificationservice.domain.notifications.NotificationEntity.NotificationIcon;
import com.hieunn.notificationservice.domain.notifications.NotificationEntity.NotificationType;
import java.time.ZonedDateTime;

public interface NotificationPort {

  @JsonInclude(JsonInclude.Include.NON_NULL)
  record NotificationRes(
      Long id,
      NotificationType type,
      String title,
      String description,
      @JsonProperty("isRead") Boolean read,
      NotificationIcon iconName,
      NotificationColor iconColorHex,
      String email,
      ZonedDateTime createdOn,
      ZonedDateTime lastModifiedOn) {
    public static NotificationRes from(NotificationEntity notificationEntity) {
      return new NotificationRes(
          notificationEntity.getId(),
          notificationEntity.getType(),
          notificationEntity.getTitle(),
          notificationEntity.getDescription(),
          notificationEntity.isRead(),
          notificationEntity.getIconName(),
          notificationEntity.getIconColorHex(),
          notificationEntity.getEmail(),
          notificationEntity.getCreatedOn(),
          notificationEntity.getLastModifiedOn());
    }

    public static NotificationRes fromWithoutEmail(NotificationEntity notificationEntity) {
      return new NotificationRes(
          notificationEntity.getId(),
          notificationEntity.getType(),
          notificationEntity.getTitle(),
          notificationEntity.getDescription(),
          notificationEntity.isRead(),
          notificationEntity.getIconName(),
          notificationEntity.getIconColorHex(),
          null, // explicitly null for email
          notificationEntity.getCreatedOn(),
          notificationEntity.getLastModifiedOn());
    }
  }
}
