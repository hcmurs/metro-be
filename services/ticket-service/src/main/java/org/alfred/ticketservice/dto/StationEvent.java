package org.alfred.ticketservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record StationEvent(
        @Positive
        @NotNull(message = "Station Route ID must be a positive number")
        Long stationRouteId,
        LocalDateTime deletedAt
){
}
