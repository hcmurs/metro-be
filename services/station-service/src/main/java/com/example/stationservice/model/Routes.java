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

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;

@Entity
@Table(name = "routes")
@Data
public class Routes {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "route_id")
  private Long routeId;

  @Column(name = "route_name")
  private String routeName;

  @Column(name = "route_code")
  private String routeCode;

  @Column(name = "distance_in_km")
  private float distanceInKm;

  //    @Column(name = "duration_in_minutes")
  //    private int quantityOfStations;
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Column(name = "is_deleted", nullable = false)
  private boolean isDeleted = false;

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
    updatedAt = createdAt;
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = LocalDateTime.now();
  }
  // Additional fields can be added as needed
}
