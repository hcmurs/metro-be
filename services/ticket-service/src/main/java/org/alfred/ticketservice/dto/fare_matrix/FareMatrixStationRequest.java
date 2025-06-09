package org.alfred.ticketservice.dto.fare_matrix;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record FareMatrixStationRequest(
        @Positive @NotNull(message = "station id is required") Long stationId
) {
}
