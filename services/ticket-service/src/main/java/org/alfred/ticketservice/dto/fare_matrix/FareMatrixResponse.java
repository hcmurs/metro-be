package org.alfred.ticketservice.dto.fare_matrix;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record FareMatrixResponse(
        Long fareMatrixId,
        String name,
        float distanceInKm,
        Long startStationId,
        Long endStationId,
        Float price,
        boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
