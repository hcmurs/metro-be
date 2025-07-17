package com.example.stationservice.dto;

import java.time.LocalDateTime;
import lombok.Data;
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
