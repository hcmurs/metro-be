package com.example.cronjob.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
