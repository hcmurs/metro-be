package com.example.stationservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "schedules")
@Data
public class Schedules {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long scheduleId;
    @Column(name="description")
    private String description;
    @Column(name="time_arrival")
    private LocalTime timeArrival;
    @Column(name="time_departure")
    private LocalTime timeDeparture;
    @Enumerated(EnumType.STRING)
    private Direction direction;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name="updated_at")
    private LocalDateTime updatedAt;
    @ManyToOne
    @JoinColumn(name = "station_id", nullable = false)
    @JsonIgnore
    private Stations station;
    public enum Direction {
        forward, backward
    }
}