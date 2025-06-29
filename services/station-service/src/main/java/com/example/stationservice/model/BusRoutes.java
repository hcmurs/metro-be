package com.example.stationservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalTime;

@Data
@Document(collection = "Route")
public class BusRoutes
{
    @Id
    private String id;
    private String name;
    private double distance;
    private int duration;
    private String start_time;
    private String end_time;
    private int is_active;
    private String route_num;
    private String direction;
    private String trip_spacing;
}
