package com.example.cronjob.Controller;

import com.example.cronjob.DTO.Request.PaymentMethodRequest;
import com.example.cronjob.DTO.Response.ApiResponse;
import com.example.cronjob.DTO.Response.PaymentMethodResponse;
import com.example.cronjob.Service.PaymentMethodService;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders/payment-methods")
public class PaymentMethodController {
    @Autowired
    private PaymentMethodService paymentMethodService;
    @PermitAll
    @GetMapping("/get-all")
    public ApiResponse<List<PaymentMethodResponse>> getAllPaymentMethods() {
        return paymentMethodService.getAllPaymentMethods();
    }

    @PostMapping("/create-payment-method")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ApiResponse<PaymentMethodResponse> createPaymentMethod(@RequestBody  PaymentMethodRequest paymentMethodRequest) {
        return paymentMethodService.createPaymentMethod(paymentMethodRequest);
    }

    @PutMapping("/update-payment-method/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ApiResponse<PaymentMethodResponse> updatePaymentMethod(@PathVariable Long id, @RequestBody PaymentMethodRequest paymentMethodRequest) {
        return paymentMethodService.updatePaymentMethod(id, paymentMethodRequest);
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/inactive-payment-method/{id}")
    public ApiResponse<PaymentMethodResponse> deletePaymentMethod(@PathVariable Long id) {
        return paymentMethodService.inActivePaymentMethod(id);
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/active-payment-method/{id}")
    public ApiResponse<PaymentMethodResponse> activePaymentMethod(@PathVariable Long id) {
        return paymentMethodService.activePaymentMethod(id);
    }

}
