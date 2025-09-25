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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hieunn.notificationservice.models.AbstractTimeAuditEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Notifications")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEntity extends AbstractTimeAuditEntity {

  public enum NotificationType {
    INFO,
    WARNING,
    SUCCESS,
    ERROR
  }

  public enum NotificationIcon {
    MESSAGE,
    ALERT,
    CHECK,
    ERROR
  }

  @Getter
  @AllArgsConstructor
  public enum NotificationColor {
    BLUE("#2196F3"),
    YELLOW("#FFEB3B"),
    GREEN("#4CAF50"),
    RED("#F44336");

    private final String hex;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, updatable = false, unique = true)
  private Long id;

  @Enumerated(EnumType.STRING)
  private NotificationType type;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "description", columnDefinition = "TEXT", nullable = false)
  private String description;

  @Column(name = "time", nullable = false)
  private LocalDateTime time;

  @Column(name = "isRead", columnDefinition = "boolean default false")
  @JsonProperty(value = "isRead")
  private boolean read;

  @Enumerated(EnumType.STRING)
  private NotificationIcon iconName;

  @Enumerated(EnumType.STRING)
  private NotificationColor iconColorHex;

  @Column(name = "email", nullable = false)
  private String email;
}
