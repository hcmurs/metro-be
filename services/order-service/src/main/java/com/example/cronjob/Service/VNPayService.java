/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Order-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.example.cronjob.Service;

import com.example.cronjob.DTO.Response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

public interface VNPayService {
  ApiResponse<Map<String, Object>> createPaymentUrl(HttpServletRequest req, Long orderId);

  ApiResponse<Map<String, Object>> paymentCallback(HttpServletRequest req);
}
