package com.hieunn.notificationservice.controllers;

import com.hieunn.notificationservice.dtos.requests.OtpRequest;
import com.hieunn.notificationservice.dtos.responses.ApiResponse;
import com.hieunn.notificationservice.services.OtpService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationController {
    OtpService otpService;

    @PostMapping("/send-otp")
    public ResponseEntity<ApiResponse<Void>> sendOtp(@RequestBody @Valid OtpRequest request) {
        otpService.generateAndSendOtp(request.getEmail(), request.getPurpose());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("Send OTP successfully"));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse<Void>> verifyOtp(
            @RequestParam String email,
            @RequestParam String otp,
            @RequestParam String purpose) {
        boolean result = otpService.verifyOtp(email, otp, purpose);
        return result ? ResponseEntity.ok(ApiResponse.success("OTP verified"))
                : ResponseEntity.ok(ApiResponse.error(401, "Invalid or expired OTP"));
    }
}
