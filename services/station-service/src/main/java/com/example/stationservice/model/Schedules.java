/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Station-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.example.stationservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Data;

@Entity
@Table(name = "schedules")
@Data
public class Schedules {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "schedule_id")
  private Long scheduleId;

  @ManyToOne
  @JoinColumn(name = "station_route_id", nullable = false)
  @JsonIgnore
  private StationRoute stationRoute;

  @Column(name = "description")
  private String description;

  @Column(name = "time_arrival")
  @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
  private LocalTime timeArrival;

  @Column(name = "time_departure")
  @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
  private LocalTime timeDeparture;

  @Enumerated(EnumType.STRING)
  private Direction direction;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
    updatedAt = createdAt;
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = LocalDateTime.now();
  }

  public enum Direction {
    forward,
    backward
  }
}
