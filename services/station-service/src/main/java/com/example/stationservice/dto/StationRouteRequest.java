package com.example.stationservice.dto;

public record StationRouteRequest(
        Long routeId,
        Integer sequenceOrder,
        Long stationId
) {
}
