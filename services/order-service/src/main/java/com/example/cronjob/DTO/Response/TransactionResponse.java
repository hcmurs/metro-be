package com.example.cronjob.DTO.Response;

import com.example.cronjob.Enum.TransactionStatus;
import com.example.cronjob.Pojos.PaymentMethod;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public class TransactionResponse {
    private Integer transactionId;
    private Integer userId;
    private Integer paymentMethodId;
    private String paymentMethodName;
    private TransactionStatus transactionStatus;
    private BigDecimal amount;
    private LocalDateTime createAt;

    public TransactionResponse() {
    }

    public TransactionResponse(Integer transactionId, Integer userId, Integer paymentMethodId, String paymentMethodName, TransactionStatus transactionStatus, BigDecimal amount, LocalDateTime createAt) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.paymentMethodId = paymentMethodId;
        this.paymentMethodName = paymentMethodName;
        this.transactionStatus = transactionStatus;
        this.amount = amount;
        this.createAt = createAt;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public Integer getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Integer paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    public void setPaymentMethodName(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }
}
