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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stations")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stations {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "station_id")
  private Long stationId;

  @Column(name = "station_code")
  private String stationCode;

  @Column(name = "name")
  private String name;

  @Column(name = "address")
  private String address;

  @Column(name = "latitude")
  private Double latitude;

  @Column(name = "longitude")
  private Double longitude;

  @Enumerated(EnumType.STRING)
  private Status status = Status.active;

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

  public enum Status {
    active,
    maintenance,
    decommissioned
  }
}
