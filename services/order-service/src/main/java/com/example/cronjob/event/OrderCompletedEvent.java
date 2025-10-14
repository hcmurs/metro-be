package com.example.cronjob.event;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderCompletedEvent  {
    private Long orderId;
    private String userEmail;
    private String tittle;
    private String body;
    private Float totalPrice;
}
