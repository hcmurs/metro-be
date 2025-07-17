package com.example.cronjob.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderTicketSingleRequest {
    private TicketRequest.FareMatrix fareMatrixId;
    private Long paymentMethodId;
}
