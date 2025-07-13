package com.example.stationservice.dto;

import com.example.stationservice.model.BusRoutes;
import com.example.stationservice.model.BusStations;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Response {
    @Builder
    public record BusStationResponse(
            String id,
            String name,
            double latitude,
            double longitude,
            Integer isActive,
            String address,
            String code,
            List<BusRoutes> routes
    ) {}
}
