package com.example.cronjob.DTO.Request;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderTicketDaysRequest {
    private TicketRequest.TicketType ticketId;
    private Long paymentMethodId;
}
