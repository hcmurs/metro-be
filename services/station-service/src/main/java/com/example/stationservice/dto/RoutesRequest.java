package com.example.stationservice.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutesRequest {
    @NotNull(message = "Route name cannot be null")
    private String routeName;
    @NotNull(message = "Route code cannot be null")
    private String routeCode;
    @NotNull(message = "Distance in km cannot be null")
    @DecimalMax(value = "10000.0", message = "DistanceInKm cannot exceed 10,000")
    @Positive(message = "DistanceInKm must be  positive")
    private float distanceInKm;

}
