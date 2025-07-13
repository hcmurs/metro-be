package com.example.cronjob.Service;

import com.example.cronjob.DTO.Request.StripeRequest;
import com.example.cronjob.DTO.Response.ApiResponse;
import com.example.cronjob.DTO.Response.StripeResponse;

import java.util.Map;

public interface StripeService {
    StripeResponse.StripePaymentResponse checkoutOrder(StripeRequest.ProductRequest request);
    Map<String, Object> paymentCallbackSuccess(String sessionId);
    Map<String, Object> paymentCallbackFailed(String sessionId);

    StripeResponse.StripePaymentMobileResponse checkoutOrderMobile(StripeRequest.ProductRequest request);
    Map<String, Object> paymentCallbackSuccessMobile(String sessionId);
    Map<String, Object> paymentCallbackFailedMobile(String sessionId);


}
