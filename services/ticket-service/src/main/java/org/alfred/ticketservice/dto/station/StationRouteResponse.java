package org.alfred.ticketservice.dto.station;


import lombok.Builder;
import org.alfred.ticketservice.model.enums.StationStatus;

import java.time.LocalDateTime;

@Builder
public record StationRouteResponse(
        Long id,
        Long RouteId,
        Integer sequenceOrder,
        StationResponse stationsResponse,
        StationStatus status,
        boolean isDeleted,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
    ) { }
