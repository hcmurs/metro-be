/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Notification-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.hieunn.notificationservice.domain.token;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserDeviceTokenRepository extends JpaRepository<UserDeviceTokenEntity, Long> {
  Optional<UserDeviceTokenEntity> findByDeviceId(String deviceId);

  @Query("SELECT u FROM user_device_tokens u WHERE u.email = :email")
  List<UserDeviceTokenEntity> findAllByEmail(@Param("email") String email);

  Optional<UserDeviceTokenEntity> findByEmailAndDeviceId(String email, String deviceId);
}
