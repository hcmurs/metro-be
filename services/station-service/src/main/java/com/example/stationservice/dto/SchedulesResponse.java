package com.example.stationservice.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
@Data
public class SchedulesResponse {
    private Long scheduleId;
    private String description;
    private LocalTime timeArrival;
    private LocalTime timeDeparture;
    private String direction;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long stationId;
    private String stationName;
}
