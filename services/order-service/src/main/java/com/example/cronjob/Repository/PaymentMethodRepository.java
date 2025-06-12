package com.example.cronjob.Repository;

import com.example.cronjob.Pojos.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Integer> {
    PaymentMethod findByPaymentMethodId(Long id);
    PaymentMethod findByName(String name);
}
