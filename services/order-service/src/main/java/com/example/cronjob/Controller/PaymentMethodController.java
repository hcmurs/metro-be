package com.example.cronjob.Controller;

import com.example.cronjob.DTO.Request.PaymentMethodRequest;
import com.example.cronjob.DTO.Response.ApiResponse;
import com.example.cronjob.DTO.Response.PaymentMethodResponse;
import com.example.cronjob.Service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/payment-methods")
public class PaymentMethodController {
    @Autowired
    private PaymentMethodService paymentMethodService;

    @GetMapping("/get-all")
    public ApiResponse<List<PaymentMethodResponse>> getAllPaymentMethods() {
        return paymentMethodService.getAllPaymentMethods();
    }

    @PostMapping("/create-payment-method")
    public ApiResponse<PaymentMethodResponse> createPaymentMethod(@RequestBody  PaymentMethodRequest paymentMethodRequest) {
        return paymentMethodService.createPaymentMethod(paymentMethodRequest);
    }

    @PutMapping("/update-payment-method/{id}")
    public ApiResponse<PaymentMethodResponse> updatePaymentMethod(@PathVariable Long id, @RequestBody PaymentMethodRequest paymentMethodRequest) {
        return paymentMethodService.updatePaymentMethod(id, paymentMethodRequest);
    }

    @DeleteMapping("/inactive-payment-method/{id}")
    public ApiResponse<PaymentMethodResponse> deletePaymentMethod(@PathVariable Long id) {
        return paymentMethodService.inActivePaymentMethod(id);
    }

    @PutMapping("/active-payment-method/{id}")
    public ApiResponse<PaymentMethodResponse> activePaymentMethod(@PathVariable Long id) {
        return paymentMethodService.activePaymentMethod(id);
    }

}
