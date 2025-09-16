/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Cronjob-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.hieunn.cronjobservice.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "station_usage_statistics")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StationUsageStatistic {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "station_id", nullable = false)
  private Long stationId;

  @Column(name = "station_name")
  private String stationName;

  @Column(name = "usage_date", nullable = false)
  private LocalDate usageDate;

  @Column(name = "entry_count")
  private Integer entryCount = 0;

  @Column(name = "exit_count")
  private Integer exitCount = 0;

  @Column(
      name = "created_at",
      updatable = false,
      insertable = false,
      columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private LocalDateTime createdAt;
}
