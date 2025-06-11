package com.example.stationservice.dto;

import com.example.stationservice.model.Schedules;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class SchedulesRequest {
    @NotNull(message = "Station ID cannot be null")
    private Long stationId;
    @NotNull(message = "Time of arrival cannot be null")
    @JsonFormat(pattern = "HH:mm",shape = JsonFormat.Shape.STRING)
    private LocalTime timeArrival;
    @NotNull(message = "Time of departure cannot be null")
    @JsonFormat(pattern = "HH:mm",shape = JsonFormat.Shape.STRING)
    private LocalTime timeDeparture;
    @NotNull(message = "Description cannot be null")
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;
    private Schedules.Direction direction;

}
