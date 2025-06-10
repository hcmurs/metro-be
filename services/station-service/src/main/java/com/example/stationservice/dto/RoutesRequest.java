package com.example.stationservice.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class RoutesRequest {
    @NotNull(message = "Route name cannot be null")
    private String routeName;
    @NotNull(message = "Route code cannot be null")
    private String routeCode;
    @NotNull(message = "Distance in km cannot be null")
    @DecimalMax(value = "10000.0", message = "DistanceInKm cannot exceed 10,000")
    @PositiveOrZero(message = "DistanceInKm must be zero or positive")
    private float distanceInKm;

}
