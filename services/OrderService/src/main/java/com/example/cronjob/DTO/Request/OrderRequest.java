package com.example.cronjob.DTO.Request;

import lombok.Data;

@Data
public class OrderRequest {
    private Long userId;
    private Long ticketId;
    private Long paymentMethodId;

    public OrderRequest() {
    }

    public OrderRequest(Long userId, Long ticketId, Long paymentMethodId) {
        this.userId = userId;
        this.ticketId = ticketId;
        this.paymentMethodId = paymentMethodId;
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

    public Long getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Long paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }
}
