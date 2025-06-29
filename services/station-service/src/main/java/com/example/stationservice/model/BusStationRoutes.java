package com.example.stationservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "route_stations")
@Data
public class BusStationRoutes {
    @Id
    private String id;
    private String routeId;
    private String stationId;
}
