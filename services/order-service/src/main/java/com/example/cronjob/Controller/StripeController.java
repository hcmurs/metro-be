package com.example.cronjob.Controller;

import com.example.cronjob.DTO.Request.StripeRequest;
import com.example.cronjob.DTO.Response.ApiResponse;
import com.example.cronjob.DTO.Response.StripeResponse;
import com.example.cronjob.Service.StripeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment/stripe")
@RequiredArgsConstructor
public class StripeController {
    private final StripeService stripeService;

    @PostMapping("/checkout")
    public ApiResponse<StripeResponse.StripePaymentResponse> checkoutOrder(@RequestBody StripeRequest.ProductRequest request) {
        return ApiResponse.success(stripeService.checkoutOrder(request));
    }

    @PostMapping("/success")
    public ApiResponse<String> paymentCallbackSuccess(@RequestParam String sessionId) {
        return ApiResponse.success(stripeService.paymentCallbackSuccess(sessionId));
    }

    @PostMapping("/failed")
    public ApiResponse<String> paymentCallbackFailed(@RequestParam String sessionId) {
        return ApiResponse.success(stripeService.paymentCallbackFailed(sessionId));
    }
}
