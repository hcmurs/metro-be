package org.alfred.ticketservice.dto.fare_matrix;

import java.time.LocalDateTime;
import lombok.Builder;
@Builder
public record FareMatrixResponse(
        Long fareMatrixId,
        String name,
        float price,
        Long startStationId,
        Long endStationId,
        boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
