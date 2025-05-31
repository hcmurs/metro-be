package com.example.stationservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StationsRequest {
    private Long routeId;
    private String stationCode;
    private String name;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer sequenceOrder;

}

