package com.example.cronjob.Service;

import com.example.cronjob.Pojos.PaymentMethod;
import com.example.cronjob.Repository.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Override
    public PaymentMethod getPaymentMethodById(Long id) {
        return paymentMethodRepository.findByPaymentMethodId(id);
    }
}
