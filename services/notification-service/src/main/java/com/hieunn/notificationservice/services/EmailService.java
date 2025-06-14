package com.hieunn.notificationservice.services;

public interface EmailService {
    void sendOtpEmail(String to, String otp);
}
