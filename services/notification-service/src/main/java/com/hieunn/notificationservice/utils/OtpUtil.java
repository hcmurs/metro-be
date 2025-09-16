/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Notification-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.hieunn.notificationservice.utils;

import java.util.concurrent.ThreadLocalRandom;
import org.springframework.stereotype.Component;

@Component
public class OtpUtil {
  public String generateOtp(int length) {
    int min = (int) Math.pow(10, length - 1);
    int max = (int) Math.pow(10, length) - 1;
    return String.valueOf(ThreadLocalRandom.current().nextInt(min, max));
  }
}
