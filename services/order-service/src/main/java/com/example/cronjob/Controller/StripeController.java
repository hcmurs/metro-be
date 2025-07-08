package com.example.cronjob.Controller;

import com.example.cronjob.DTO.Request.StripeRequest;
import com.example.cronjob.DTO.Response.ApiResponse;
import com.example.cronjob.DTO.Response.StripeResponse;
import com.example.cronjob.Service.StripeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payment/stripe")
@RequiredArgsConstructor
public class StripeController {
    private final StripeService stripeService;

    @PostMapping("/checkout")
    public ApiResponse<StripeResponse.StripePaymentResponse> checkoutOrder(@RequestBody StripeRequest.ProductRequest request) {
        return ApiResponse.success(stripeService.checkoutOrder(request));
    }

    @GetMapping("/success")
    public ApiResponse<Map<String, Object>> paymentCallbackSuccess(@RequestParam String session_id) {
        return ApiResponse.success(stripeService.paymentCallbackSuccess(session_id));
    }

    @GetMapping("/failed")
    public ApiResponse<Map<String, Object>> paymentCallbackFailed(@RequestParam String session_id) {
        return ApiResponse.success(stripeService.paymentCallbackFailed(session_id));
    }
}
