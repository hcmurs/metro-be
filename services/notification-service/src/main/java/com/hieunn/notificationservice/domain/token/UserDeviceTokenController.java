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

import com.hieunn.notificationservice.domain.token.FcmTokenPort.CreateUserDeviceTokenReq;
import com.hieunn.notificationservice.domain.token.FcmTokenPort.UpdateUserDeviceTokenReq;
import com.hieunn.notificationservice.domain.token.FcmTokenPort.UserDeviceTokenDto;
import com.hieunn.notificationservice.dtos.UserDto;
import com.hieunn.notificationservice.thirdparty.UserClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-device-tokens")
@RequiredArgsConstructor
@Tag(name = "user-device-tokens", description = "User Device Token API")
public class UserDeviceTokenController {

  private final FcmTokenServiceImpl service;
  private final UserClient userClient;

  @GetMapping
  @Operation(
      summary = "Get all user device tokens",
      description = "Fetches all user device tokens with pagination support.")
  public Page<UserDeviceTokenDto> getAll(@PageableDefault(size = 20) Pageable pageable) {
    return service.getAll(pageable);
  }

  @GetMapping("/{id}")
  @Operation(
      summary = "Get user device token by ID",
      description = "Fetches a user device token by its unique ID.")
  public UserDeviceTokenDto getById(@PathVariable Long id) {
    return service.getById(id);
  }

  @PostMapping
  @Operation(
      summary = "Create or update user device token",
      description =
          "Creates a new user device token or updates an existing one based on the provided request.")
  public ResponseEntity<UserDeviceTokenDto> createOrUpdate(
      @RequestBody CreateUserDeviceTokenReq req) {
    String token =
        SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
    UserDto data = userClient.getCurrentUserFromDB("Bearer " + token).getData();

    // If no userId provided in request, use authenticated user
    String userId = req.email() != null ? req.email() : data.getEmail();

    if (userId == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    var updatedReq =
        new CreateUserDeviceTokenReq(
            userId, req.deviceId(), req.fcmToken(), req.deviceName(), req.platform());

    return ResponseEntity.status(HttpStatus.CREATED).body(service.createOrUpdate(updatedReq));
  }

  @PatchMapping("/{id}")
  @Operation(
      summary = "Update user device token",
      description = "Updates an existing user device token by its unique ID.")
  public UserDeviceTokenDto update(
      @PathVariable Long id, @RequestBody UpdateUserDeviceTokenReq req) {
    return service.update(id, req);
  }

  @GetMapping("/user/{email}/tokens")
  @Operation(
      summary = "Get FCM tokens by user email",
      description = "Fetches all FCM tokens associated with a specific user email.")
  public List<String> getFcmTokensByEmail(@PathVariable String email) {
    return service.getFcmTokensByEmail(email);
  }

  @GetMapping("/my-tokens")
  @Operation(
      summary = "Get my FCM tokens",
      description = "Fetches all FCM tokens associated with the currently authenticated user.")
  public ResponseEntity<List<String>> getMyFcmTokens() {
    String token =
        SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
    var data = userClient.getCurrentUserFromDB("Bearer " + token).getData().getEmail();
    if (data == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    return ResponseEntity.ok(service.getFcmTokensByEmail(data));
  }
}
