package com.example.stationservice.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class StationsRequest {
    @NotNull(message = "Station code cannot be null")
    private String stationCode;
    @NotNull(message = "Name cannot be null")
    private String name;
    @NotNull(message = "Address cannot be null")
    private String address;
    @NotNull(message = "Latitude cannot be null")
    private Double latitude;
    @NotNull(message = "Longitude cannot be null")
    private Double longitude;
}

