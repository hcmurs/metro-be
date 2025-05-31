package com.example.stationservice.dto;

import com.example.stationservice.model.Schedules;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class SchedulesRequest {
    private Long stationId;
    private LocalTime timeArrival;
    private LocalTime timeDeparture;
    private String description;
    private Schedules.Direction direction;

}
