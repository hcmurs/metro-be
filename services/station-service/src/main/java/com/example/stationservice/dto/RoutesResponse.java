package com.example.stationservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RoutesResponse {
    private Long routeId;
    private String routeName;
    private String routeCode;
    private float distanceInKm;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isDeleted;
}
