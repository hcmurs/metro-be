package com.example.cronjob.Service;

import com.example.cronjob.DTO.Request.PaymentMethodRequest;
import com.example.cronjob.DTO.Response.ApiResponse;
import com.example.cronjob.DTO.Response.PaymentMethodResponse;
import com.example.cronjob.Mapping.PaymentMethodMapping;
import com.example.cronjob.Pojos.PaymentMethod;
import com.example.cronjob.Repository.PaymentMethodRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private PaymentMethodMapping paymentMethodMapping;

    @Override
    public PaymentMethod getPaymentMethodById(Long id) {
        return paymentMethodRepository.findByPaymentMethodId(id);
    }

    @Override
    public ApiResponse<List<PaymentMethodResponse>> getAllPaymentMethods() {
        return ApiResponse.<List<PaymentMethodResponse>>builder()
                .status(200)
                .message("Payment methods retrieved successfully")
                .data(paymentMethodMapping.toResponseList(paymentMethodRepository.findAll()))
                .build();
    }

    @Override
    public ApiResponse<PaymentMethodResponse> createPaymentMethod(PaymentMethodRequest paymentMethodRequest) {
        PaymentMethod existingMethods = paymentMethodRepository.findByName(paymentMethodRequest.getPaymentMethodName());
        if(existingMethods != null) {
            throw new EntityNotFoundException("Payment method already exists with name: " + paymentMethodRequest.getPaymentMethodName());
        }
        PaymentMethod paymentMethod = PaymentMethod.builder()
                .name(paymentMethodRequest.getPaymentMethodName())
                .isActive(true)
                .build();
        PaymentMethod savedPaymentMethod = paymentMethodRepository.save(paymentMethod);
        PaymentMethodResponse response = paymentMethodMapping.toResponse(savedPaymentMethod);

        return ApiResponse.<PaymentMethodResponse>builder()
                .status(201)
                .message("Payment method created successfully")
                .data(response)
                .build();
    }

    @Override
    public ApiResponse<PaymentMethodResponse> updatePaymentMethod(Long id, PaymentMethodRequest paymentMethodRequest) {
        PaymentMethod updatePaymentMethod = paymentMethodRepository.findByPaymentMethodId(id);
        if (updatePaymentMethod == null) {
            throw new EntityNotFoundException("Payment method not found with ID: " + id);
        }
        updatePaymentMethod.setName(paymentMethodRequest.getPaymentMethodName());
        return ApiResponse.<PaymentMethodResponse>builder()
                .status(200)
                .message("Payment method updated successfully")
                .data(paymentMethodMapping.toResponse(paymentMethodRepository.save(updatePaymentMethod)))
                .build();
    }

    @Override
    public ApiResponse<PaymentMethodResponse> inActivePaymentMethod(Long id) {
        PaymentMethod deletePaymentMethod = paymentMethodRepository.findByPaymentMethodId(id);
        if (deletePaymentMethod == null) {
            throw new EntityNotFoundException("Payment method not found with ID: " + id);
        }
        deletePaymentMethod.setActive(false);
        paymentMethodRepository.save(deletePaymentMethod);
        return ApiResponse.<PaymentMethodResponse>builder()
                .status(200)
                .message("Payment method deleted successfully")
                .data(paymentMethodMapping.toResponse(deletePaymentMethod))
                .build();
    }

    @Override
    public ApiResponse<PaymentMethodResponse> activePaymentMethod(Long id) {
        PaymentMethod deletePaymentMethod = paymentMethodRepository.findByPaymentMethodId(id);
        if (deletePaymentMethod == null) {
            throw new EntityNotFoundException("Payment method not found with ID: " + id);
        }
        deletePaymentMethod.setActive(true);
        paymentMethodRepository.save(deletePaymentMethod);
        return ApiResponse.<PaymentMethodResponse>builder()
                .status(200)
                .message("Payment method activated successfully")
                .data(paymentMethodMapping.toResponse(deletePaymentMethod))
                .build();
    }
}
