package com.example.cronjob.Pojos;
import com.example.cronjob.Enum.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    @Column(name = "stripe_session_id",nullable = true)
    private String stripeSessionId;

    @Column(nullable = false, updatable = false, name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "order", fetch = FetchType.EAGER)
    private Transactions transaction;


}
