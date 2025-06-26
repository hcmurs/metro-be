package com.example.stationservice.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
public class StationsResponse {
    private Long stationId;
    private String stationCode;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private Integer sequenceOrder;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long routeId;
}
