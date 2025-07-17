package com.example.stationservice.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class RoutesResponse {
    private Long routeId;
    private String routeName;
    private String routeCode;
    private float distanceInKm;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
