package com.example.stationservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalTime;

@Entity
@Table(name = "schedules")
@Data
public class Schedules {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer scheduleId;

    private Integer stationId;
    private String description;

    private LocalTime timeArrival;
    private LocalTime timeDeparture;

    @Enumerated(EnumType.STRING)
    private Direction direction;

    private Timestamp createdAt;
    private Timestamp updatedAt;

    public enum Direction {
        forward, backward
    }
}