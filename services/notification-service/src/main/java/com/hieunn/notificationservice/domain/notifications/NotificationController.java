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

import com.hieunn.notificationservice.domain.notifications.NotificationPort.NotificationRes;
import com.hieunn.notificationservice.dtos.requests.OtpRequest;
import com.hieunn.notificationservice.dtos.responses.ApiResponse;
import com.hieunn.notificationservice.services.OtpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
@Tag(name = "notifications", description = "Notification API")
public class NotificationController {

  private final NotificationService service;
  private final OtpService otpService;

  @GetMapping("/page")
  public ApiResponse<Page<NotificationRes>> getAllPageable(
      @ParameterObject
          @Parameter(
              description = "Pagination information",
              schema = @Schema(defaultValue = "{\"page\":0,\"size\":2,\"sort\":[\"id,desc\"]}"))
          @PageableDefault(size = 20)
          Pageable pageable) {
    return ApiResponse.success(service.getAllNotifications(pageable));
  }

  @Deprecated(since = "Use pageable version instead", forRemoval = true)
  @GetMapping
  public List<NotificationEntity> getAll() {
    return service.getAllNotifications();
  }

  @GetMapping("/{email}")
  @Operation(
      summary = "Get notifications by user ID",
      description = "Fetches all notifications for a specific user based on their user ID.")
  public ResponseEntity<?> getByUserId(
      @PathVariable @Schema(defaultValue = "hoangclw@gmail.com") String email) {
    List<NotificationPort.NotificationRes> notifications = service.getNotificationByEmail(email);
    return ResponseEntity.ok(notifications);
  }

  @PostMapping("")
  @Operation(
      summary = "[DEBUG] Add a new notification",
      description = "Creates a new notification and saves it to the database.")
  public ResponseEntity<?> create(@Valid @RequestBody NotificationEntity notificationEntity) {

    service.addNotification(notificationEntity);
    return ResponseEntity.ok("Notification created successfully");
  }

  @PatchMapping("/{id}/read")
  public ResponseEntity<?> markAsRead(@PathVariable Long id) {
    service.markAsRead(id);
    return ResponseEntity.ok().build();
  }

  @Operation(
      summary = "[DEBUG] Send notification to a specific device",
      description = "Sends a push notification to a specific device using its FCM token.")
  @GetMapping("/send-to-mobile/{token}")
  public ResponseEntity<?> send(
      @PathVariable @Schema(defaultValue = "fcm_device_token_here") String token) throws Exception {
    try {
      service.sendNotification(token);
      return ResponseEntity.ok("Sent!");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
    }
  }

  @PostMapping("/send-to-user")
  @Operation(
      summary = "[PRODUCTION] Send notification to all user devices",
      description =
          "Send push notification to all registered devices of a user and save to database")
  public ResponseEntity<?> sendToUser(
      @RequestParam @Schema(defaultValue = "hoangclw@gmail.com") String email,
      @RequestParam @Schema(defaultValue = "Test Notification") String title,
      @RequestParam @Schema(defaultValue = "This is a test notification sent to all your devices")
          String body,
      @RequestParam(defaultValue = "INFO") NotificationEntity.NotificationType type)
      throws Exception {
    service.sendNotificationToUser(email, title, body, type);
    return ResponseEntity.ok("Notification sent to all devices for user: " + email);
  }

  @PostMapping("/send-otp")
  public ResponseEntity<ApiResponse<Void>> sendOtp(@RequestBody @Valid OtpRequest request) {
    otpService.generateAndSendOtp(request.getEmail(), request.getPurpose());
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Send OTP successfully"));
  }

  @PostMapping("/verify-otp")
  public ResponseEntity<ApiResponse<Void>> verifyOtp(
      @RequestParam String email, @RequestParam String otp, @RequestParam String purpose) {
    boolean result = otpService.verifyOtp(email, otp, purpose);
    return result
        ? ResponseEntity.ok(ApiResponse.success("OTP verified"))
        : ResponseEntity.ok(ApiResponse.error(401, "Invalid or expired OTP"));
  }
}
