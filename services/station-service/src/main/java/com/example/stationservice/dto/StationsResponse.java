package com.example.stationservice.dto;

import com.example.stationservice.model.Stations;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@Builder
public class StationsResponse {
    private Long stationId;
    private String stationCode;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private boolean isDeleted;
    private Stations.Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
