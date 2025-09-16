/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Order-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.example.cronjob.Repository;

import com.example.cronjob.Pojos.Orders;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
  Orders findByOrderId(Long orderId);

  boolean existsByOrderId(Long orderId);

  List<Orders> findByUserId(Long userId);

  Orders findByStripeSessionId(String stripeSessionId);

  boolean existsByUserId(Long userId);
}
