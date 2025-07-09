package com.example.cronjob.Repository;

import com.example.cronjob.Pojos.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    Orders findByOrderId(Long orderId);
    boolean existsByOrderId(Long orderId);
    List<Orders> findByUserId(Long userId);
    Orders findByStripeSessionId(String stripeSessionId);
    boolean existsByUserId(Long userId);
}
