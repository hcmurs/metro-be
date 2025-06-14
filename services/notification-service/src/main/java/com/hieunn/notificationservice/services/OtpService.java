package com.hieunn.notificationservice.services;

public interface OtpService {
    void generateAndSendOtp(String email, String purpose);
    boolean verifyOtp(String email, String otp, String purpose);
}
