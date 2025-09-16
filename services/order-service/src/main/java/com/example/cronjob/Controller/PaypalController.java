/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Order-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.example.cronjob.Controller;

import com.example.cronjob.DTO.Response.ApiResponse;
import com.example.cronjob.Service.PaypalServiceImpl;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment/paypal")
@RequiredArgsConstructor
public class PaypalController {

  private final PaypalServiceImpl paypalService;

  @PostMapping("/create")
  @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
  public ApiResponse<?> createPayment(@RequestParam Long orderId) {
    String approvalLink = paypalService.createOrder(orderId, "USD", "Payment by PayPal");

    return ApiResponse.builder()
        .status(200)
        .message("Payment created successfully")
        .data(Map.of("approvalLink", approvalLink))
        .build();
  }

  @GetMapping("/success")
  @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
  public ApiResponse<?> paymentSuccess(@RequestParam("token") String token) {
    Long orderIdLong = paypalService.getCustomOrderIdFromPayPalOrderId(token);
    paypalService.updateOrderSuccess(orderIdLong);
    return ApiResponse.builder()
        .status(200)
        .message("Payment completed successfully")
        .data(Map.of("message", "Payment success!", "token", token))
        .build();
  }

  @GetMapping("/cancel")
  @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
  public ApiResponse<?> paymentCancel(@RequestParam("token") String token) {
    Long orderIdLong = paypalService.getCustomOrderIdFromPayPalOrderId(token);
    paypalService.updateOrderFail(orderIdLong);
    return ApiResponse.builder()
        .status(400)
        .message("Payment cancelled by user")
        .data(Map.of("message", "Payment cancelled."))
        .build();
  }
}
