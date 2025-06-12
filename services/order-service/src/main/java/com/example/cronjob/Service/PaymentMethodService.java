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
    ApiResponse<PaymentMethodResponse> updatePaymentMethod(Long id, PaymentMethodRequest paymentMethodRequest);
    ApiResponse<PaymentMethodResponse> inActivePaymentMethod(Long id);
    ApiResponse<PaymentMethodResponse> activePaymentMethod(Long id);

}
