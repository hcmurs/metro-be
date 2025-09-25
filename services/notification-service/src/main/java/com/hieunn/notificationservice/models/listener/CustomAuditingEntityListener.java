/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Notification-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.hieunn.notificationservice.models.listener;

import com.hieunn.notificationservice.models.AbstractAuditEntity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.auditing.AuditingHandler;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Configurable
public class CustomAuditingEntityListener extends AuditingEntityListener {

  public CustomAuditingEntityListener(ObjectFactory<AuditingHandler> handler) {
    super.setAuditingHandler(handler);
  }

  @Override
  @PrePersist
  public void touchForCreate(Object target) {
    if (!(target instanceof AbstractAuditEntity entity)) {
      super.touchForCreate(target);
      return;
    }
    if (entity.getCreatedBy() == null) {
      super.touchForCreate(target);
    } else if (entity.getLastModifiedBy() == null) {
      entity.setLastModifiedBy(entity.getCreatedBy());
    }
  }

  @Override
  @PreUpdate
  public void touchForUpdate(Object target) {
    super.touchForUpdate(target);
  }
}
