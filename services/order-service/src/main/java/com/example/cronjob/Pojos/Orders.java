/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Order-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.example.cronjob.Pojos;

import com.example.cronjob.Enum.OrderStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Table(name = "orders")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Orders {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_id")
  private Long orderId;

  @Column(nullable = false, name = "user_id")
  private Long userId;

  @Column(nullable = false, name = "ticket_id")
  private Long ticketId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, name = "status")
  private OrderStatus status;

  @Column(precision = 10, scale = 2, name = "amount")
  private BigDecimal amount;

  @Column(name = "stripe_session_id", nullable = true)
  private String stripeSessionId;

  @Column(nullable = false, updatable = false, name = "created_at")
  private LocalDateTime createdAt = LocalDateTime.now();

  private LocalDateTime updatedAt;

  @OneToOne(mappedBy = "order", fetch = FetchType.EAGER)
  private Transactions transaction;
}
