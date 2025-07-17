package org.alfred.ticketservice.dto.station;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
@Builder
public record StationResponse(
         Long stationId,
         String stationCode,
         String name,
         String address,
         BigDecimal latitude,
         BigDecimal longitude,
         Integer sequenceOrder,
         String status,
         LocalDateTime createdAt,
         LocalDateTime updatedAt,
         Long routeId
) {

}
