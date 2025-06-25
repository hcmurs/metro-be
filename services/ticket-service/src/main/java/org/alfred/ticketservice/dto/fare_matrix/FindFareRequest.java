package org.alfred.ticketservice.dto.fare_matrix;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record FindFareRequest(
        @NotNull(message = "Start station ID is required")
        @PositiveOrZero(message = "Start station ID must be valid")
        Long startStationId,

        @NotNull(message = "End station ID is required")
        @PositiveOrZero(message = "End station ID must be valid")
        Long endStationId
) {
}
