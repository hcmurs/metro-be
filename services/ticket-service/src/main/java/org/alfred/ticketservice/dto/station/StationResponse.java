package org.alfred.ticketservice.dto.station;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Builder
public record StationResponse(
         Long stationId,
         String stationCode,
         String name,
         String address,
         Double latitude,
         Double longitude,
         boolean deleted,
         String status,
         LocalDateTime createdAt,
         LocalDateTime updatedAt
) {

}
