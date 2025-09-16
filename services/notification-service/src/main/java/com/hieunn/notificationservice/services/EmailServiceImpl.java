/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Notification-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.hieunn.notificationservice.services;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
  JavaMailSender mailSender;
  SpringTemplateEngine templateEngine;

  @Override
  public void sendOtpEmail(String to, String otp) {
    Context context = new Context();
    context.setVariable("otp", otp);

    String htmlContent = templateEngine.process("otp-mail-template", context);

    MimeMessagePreparator messagePreparator =
        mimeMessage -> {
          mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
          mimeMessage.setFrom(new InternetAddress("hieunnse183474@fpt.edu.vn"));
          mimeMessage.setSubject("Your OTP Code");
          mimeMessage.setContent(htmlContent, "text/html; charset=utf-8");
        };

    mailSender.send(messagePreparator);
  }
}
