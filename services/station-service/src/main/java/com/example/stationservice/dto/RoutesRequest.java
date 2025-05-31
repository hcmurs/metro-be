package com.example.stationservice.dto;

import lombok.Data;

@Data
public class RoutesRequest {
    private String routeName;
    private String routeCode;
    private float distanceInKm;


}
