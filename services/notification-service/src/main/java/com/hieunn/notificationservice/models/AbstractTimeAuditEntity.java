/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Notification-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.hieunn.notificationservice.models;

import com.hieunn.notificationservice.models.listener.CustomTimeAuditingEntityListener;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.ZonedDateTime;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@MappedSuperclass
@Getter
@Setter
@EntityListeners(CustomTimeAuditingEntityListener.class)
public class AbstractTimeAuditEntity {
  @CreationTimestamp private ZonedDateTime createdOn;

  @UpdateTimestamp private ZonedDateTime lastModifiedOn;

  @Column(name = "is_deleted", nullable = false)
  private boolean deleted = false;
}
