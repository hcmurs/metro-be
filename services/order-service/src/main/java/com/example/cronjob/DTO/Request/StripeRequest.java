package com.example.cronjob.DTO.Request;

import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
public class StripeRequest {
    @Builder
    public record ProductRequest(
            @Positive
            Long orderId
    ){}
}
