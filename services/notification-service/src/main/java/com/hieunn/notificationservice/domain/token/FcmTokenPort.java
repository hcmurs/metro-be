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

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public interface FcmTokenPort {

  record UserDeviceTokenResponse(
      Long id,
      String email,
      String deviceId,
      String fcmToken,
      String deviceName,
      String platform) {}

  record CreateUserDeviceTokenReq(
      @Email(message = "Invalid email format") @NotBlank(message = "Email is required")
          String email,
      @NotBlank(message = "Device ID is required") String deviceId,
      @NotBlank(message = "FCM token is required")
          @Pattern(regexp = "^[a-zA-Z0-9\\-\\_:]+$", message = "Invalid FCM token format")
          String fcmToken,
      @NotBlank(message = "Device name is required") String deviceName,
      @NotBlank(message = "Platform is required") String platform) {}

  record UpdateUserDeviceTokenReq(String fcmToken, String deviceName, String platform) {}
}
