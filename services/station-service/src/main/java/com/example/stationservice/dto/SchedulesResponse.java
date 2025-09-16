/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Station-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.example.stationservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Data;

@Data
public class SchedulesResponse {
  private Long scheduleId;
  private String description;

  @JsonFormat(pattern = "HH:mm")
  private LocalTime timeArrival;

  @JsonFormat(pattern = "HH:mm")
  private LocalTime timeDeparture;

  private String direction;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private Long stationId;
  private String stationName;
}
