package com.example.stationservice.dto;

import com.example.stationservice.model.StationRoute;
import com.example.stationservice.model.Stations;
import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record StationRouteResponse(
        Long id,
        Long RouteId,
        Integer sequenceOrder,
        StationsResponse stationsResponse,
        Stations.Status status,
        boolean isDeleted,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
    ) { }
