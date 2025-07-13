package com.example.cronjob.DTO.Response;

import lombok.Builder;
import lombok.Data;

@Data
public class StripeResponse {
    @Builder
    public record StripePaymentResponse(
            String status,
            String message,
            String sessionId,
            String sessionUrl
    ) { }

    @Builder
    public record StripePaymentMobileResponse(
            String status,
            String message,
            String sessionId,
            String clientSecret
    ){}

}
