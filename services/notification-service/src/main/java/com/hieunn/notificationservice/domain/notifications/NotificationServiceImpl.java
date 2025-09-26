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

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import com.hieunn.notificationservice.domain.notifications.NotificationPort.NotificationRes;
import com.hieunn.notificationservice.domain.token.FcmTokenService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

  private final NotificationRepository repository;
  private final FcmTokenService fcmTokenService;

  @Override
  public List<NotificationEntity> getAllNotifications() {
    return repository.findAll();
  }

  @Override
  public Page<NotificationRes> getAllNotifications(Pageable pageable) {
    return repository.findAll(pageable).map(NotificationRes::fromWithoutEmail);
  }

  @Override
  public List<NotificationRes> getNotificationByEmail(String email) {
    return repository.findByEmailOrderByCreatedOnDesc(email).stream()
        .map(NotificationRes::fromWithoutEmail)
        .toList();
  }

  @Override
  public void addNotification(NotificationEntity notification) {
    var newNotification =
        NotificationEntity.builder()
            .title(notification.getTitle())
            .type(notification.getType())
            .description(notification.getDescription())
            .email("hoangclw@gmail.com")
            .iconName(notification.getIconName())
            .iconColorHex(notification.getIconColorHex())
            .build();

    repository.save(newNotification);
  }

  @Override
  public void markAsRead(Long id) {
    var notification = repository.findById(id).orElseThrow();
    if (!notification.isRead()) {
      notification.setRead(true);
      repository.save(notification);
    }
  }

  @Override
  public void sendNotification(String token) throws Exception {
    Notification notification =
        Notification.builder()
            .setTitle("Hello from HCMURS")
            .setBody("This is a test notification")
            .build();

    Message message = Message.builder().setToken(token).setNotification(notification).build();

    String response = FirebaseMessaging.getInstance().send(message);
    log.info("Sent notification! Response: {}", response);
  }

  @Override
  public void sendNotificationToUser(
      String email, String title, String body, NotificationEntity.NotificationType type)
      throws Exception {
    // Get all FCM tokens for this user
    List<String> fcmTokens = fcmTokenService.getFcmTokensByEmail(email);

    if (fcmTokens.isEmpty()) {
      log.warn("No FCM tokens found for user: {}", email);
      return;
    }

    // Create Firebase notification
    Notification notification = Notification.builder().setTitle(title).setBody(body).build();

    if (fcmTokens.size() == 1) {
      // Send to single device
      Message message =
          Message.builder().setToken(fcmTokens.get(0)).setNotification(notification).build();

      String response = FirebaseMessaging.getInstance().send(message);
      log.info("Sent notification to user {} (single device). Response: {}", email, response);
    } else {
      // Send to multiple devices
      MulticastMessage message =
          MulticastMessage.builder().addAllTokens(fcmTokens).setNotification(notification).build();

      var response = FirebaseMessaging.getInstance().sendEachForMulticast(message);
      log.info(
          "Sent notification to user {} ({} devices). Success: {}, Failure: {}",
          email,
          fcmTokens.size(),
          response.getSuccessCount(),
          response.getFailureCount());
    }

    // Save notification to database for user to view in app
    saveNotificationForUser(
        email,
        title,
        body,
        type,
        NotificationEntity.NotificationIcon.MESSAGE,
        NotificationEntity.NotificationColor.BLUE);
  }

  @Override
  public NotificationEntity saveNotificationForUser(
      String email,
      String title,
      String description,
      NotificationEntity.NotificationType type,
      NotificationEntity.NotificationIcon icon,
      NotificationEntity.NotificationColor color) {
    var notification =
        NotificationEntity.builder()
            .title(title)
            .type(type)
            .description(description)
            .email(email)
            .iconName(icon)
            .iconColorHex(color)
            .read(false)
            .build();

    return repository.save(notification);
  }
}
