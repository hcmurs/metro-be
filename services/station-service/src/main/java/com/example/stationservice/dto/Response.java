package com.example.stationservice.dto;

import com.example.stationservice.model.BusRoutes;
import java.util.List;
import lombok.Builder;
import lombok.Data;

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
