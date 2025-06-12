package com.hieunn.notificationservice.services;

public interface EmailService {
    void sendEmail(String to, String subject, String content);
}
