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

import com.example.cronjob.DTO.Request.PaymentMethodRequest;
import com.example.cronjob.DTO.Response.ApiResponse;
import com.example.cronjob.DTO.Response.PaymentMethodResponse;
import com.example.cronjob.Pojos.PaymentMethod;
import java.util.List;

public interface PaymentMethodService {
  PaymentMethod getPaymentMethodById(Long id);

  ApiResponse<List<PaymentMethodResponse>> getAllPaymentMethods();

  ApiResponse<PaymentMethodResponse> createPaymentMethod(PaymentMethodRequest paymentMethodRequest);

  ApiResponse<PaymentMethodResponse> updatePaymentMethod(
      Long id, PaymentMethodRequest paymentMethodRequest);

  ApiResponse<PaymentMethodResponse> inActivePaymentMethod(Long id);

  ApiResponse<PaymentMethodResponse> activePaymentMethod(Long id);
}
