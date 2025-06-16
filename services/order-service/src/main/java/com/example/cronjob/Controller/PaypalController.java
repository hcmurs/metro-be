package com.example.cronjob.Controller;

import com.example.cronjob.DTO.Request.PaypalOrderRequest;
import com.example.cronjob.DTO.Response.ApiResponse;
import com.example.cronjob.Service.PaypalServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payment/paypal")
@RequiredArgsConstructor
public class PaypalController {

    private final PaypalServiceImpl paypalService;

    @PostMapping("/create")
    public ApiResponse<?> createPayment(@RequestBody PaypalOrderRequest request) {
        String approvalLink = paypalService.createOrder(
                request.getAmount(),
                request.getCurrency() != null ? request.getCurrency() : "USD",
                request.getDescription() != null ? request.getDescription() : "Payment by PayPal"
        );

        return ApiResponse.builder()
                .status(200)
                .message("Payment created successfully")
                .data(Map.of("approvalLink", approvalLink))
                .build();
    }

    @GetMapping("/success")
    public ApiResponse<?> paymentSuccess(@RequestParam("token") String token) {
        return ApiResponse.builder()
                .status(200)
                .message("Payment completed successfully")
                .data(Map.of("message", "Payment success!", "token", token))
                .build();
    }

    @GetMapping("/cancel")
    public ApiResponse<?> paymentCancel() {
        return ApiResponse.builder()
                .status(400)
                .message("Payment cancelled by user")
                .data(Map.of("message", "Payment cancelled."))
                .build();
    }
}
