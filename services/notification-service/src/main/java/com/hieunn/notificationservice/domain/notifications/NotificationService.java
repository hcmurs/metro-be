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

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationService {

  List<NotificationEntity> getAllNotifications();

  Page<NotificationEntity> getAllNotifications(Pageable pageable);

  List<NotificationPort.NotificationRes> getNotificationByEmail(String email);

  void addNotification(NotificationEntity notificationEntity);

  void markAsRead(Long id);

  void sendNotification(String token) throws Exception;

  void sendNotificationToUser(
      String email, String title, String body, NotificationEntity.NotificationType type)
      throws Exception;

  NotificationEntity saveNotificationForUser(
      String email,
      String title,
      String description,
      NotificationEntity.NotificationType type,
      NotificationEntity.NotificationIcon icon,
      NotificationEntity.NotificationColor color);
}
