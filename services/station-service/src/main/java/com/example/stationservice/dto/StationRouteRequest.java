package com.example.stationservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record StationRouteRequest(
        @NotNull(message = "Route ID is required")
        Long routeId,

        @NotNull(message = "Sequence order is required")
        @Positive(message = "Sequence order must be positive")
        Integer sequenceOrder,

        @NotNull(message = "Station ID is required")
        Long stationId
) {
}
