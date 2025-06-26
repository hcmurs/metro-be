package com.example.cronjob.DTO.Request;

import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderTicketSingleRequest {
    private TicketRequest.FareMatrix fareMatrixId;
    private Long paymentMethodId;
}
