package com.example.cronjob.Service;

import com.example.cronjob.DTO.Request.StripeRequest;
import com.example.cronjob.DTO.Response.StripeResponse;

public interface StripeService {
    StripeResponse.StripePaymentResponse checkoutOrder(StripeRequest.ProductRequest request);
    String paymentCallbackSuccess(String sessionId);
    String paymentCallbackFailed(String sessionId);
}
