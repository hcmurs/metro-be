package com.example.stationservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "Bus")
public class BusStations {
    @Id
    private String id;
    private String name;
    private Double latitude;
    private Double longitude;
    private Integer isActive;
    private String address;
    private String code;
}
