package com.hieunn.notificationservice.controllers;

import com.hieunn.notificationservice.dtos.requests.OtpRequest;
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
    public ResponseEntity<?> sendOtp(@RequestBody @Valid OtpRequest request) {
        otpService.generateAndSendOtp(request.getEmail(), request.getPurpose());
        return ResponseEntity.ok("OTP sent");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestParam String email,
                                       @RequestParam String otp,
                                       @RequestParam String purpose) {
        boolean result = otpService.verifyOtp(email, otp, purpose);
        return result ? ResponseEntity.ok("OTP verified")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP");
    }
}
