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
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {

  List<NotificationEntity> findByEmail(String email);

  List<NotificationEntity> findByEmailOrderByCreatedOnDesc(String email);

  Page<NotificationEntity> findAllByActiveTrue(Pageable pageable);

  List<NotificationEntity> findByEmailAndActiveTrueOrderByCreatedOnDesc(String email);

}
