package com.example.stationservice.model;

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
    @Column(name = "schedule_id")
    private Long scheduleId;
    @Column(name="station_id")
    private Integer stationId;
    @Column(name="description")
    private String description;
    @Column(name="time_arrival")
    private LocalTime timeArrival;
    @Column(name="time_departure")
    private LocalTime timeDeparture;

    @Enumerated(EnumType.STRING)
    private Direction direction;
    @Column (name = "created_at")
    private Timestamp createdAt;
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public enum Direction {
        forward, backward
    }
}