package com.example.cronjob.DTO.Response;

import com.example.cronjob.Enum.OrderStatus;
import com.example.cronjob.Pojos.Transactions;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public class OrderResponse {
    private Long orderId;
    private Long userId;
    private Long ticketId;
    private OrderStatus status;
    private BigDecimal amount;
    private LocalDateTime createdAt;
    private TransactionResponse transaction;
    @Builder
    public record OrderDetailResponse(
            Long orderId,
            Long userId,
            OrderStatus status,
            BigDecimal amount,
            TicketResponse ticket
    ) {
    }

    public OrderResponse() {
    }

    public OrderResponse(Long orderId, Long userId, Long ticketId, OrderStatus status, BigDecimal amount, LocalDateTime createdAt, TransactionResponse transaction) {
        this.orderId = orderId;
        this.userId = userId;
        this.ticketId = ticketId;
        this.status = status;
        this.amount = amount;
        this.createdAt = createdAt;
        this.transaction = transaction;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public TransactionResponse getTransaction() {
        return transaction;
    }

    public void setTransaction(TransactionResponse transaction) {
        this.transaction = transaction;
    }
}
