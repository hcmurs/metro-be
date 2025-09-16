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

import com.example.cronjob.DTO.Request.StripeRequest;
import com.example.cronjob.DTO.Response.StripeResponse;
import java.util.Map;

public interface StripeService {
  StripeResponse.StripePaymentResponse checkoutOrder(StripeRequest.ProductRequest request);

  Map<String, Object> paymentCallbackSuccess(String sessionId);

  Map<String, Object> paymentCallbackFailed(String sessionId);

  StripeResponse.StripePaymentMobileResponse checkoutOrderMobile(
      StripeRequest.ProductRequest request);

  Map<String, Object> paymentCallbackSuccessMobile(String sessionId);

  Map<String, Object> paymentCallbackFailedMobile(String sessionId);
}
