package com.example.cronjob.DTO.Response;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodResponse {
    private Integer paymentMethodId;
    private String paymentMethodName;
    private boolean isActive;
}
