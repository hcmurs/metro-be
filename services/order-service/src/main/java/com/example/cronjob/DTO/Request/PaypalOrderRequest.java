package com.example.cronjob.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaypalOrderRequest {
    private Double amount;
    private String currency;
    private String description;
}
