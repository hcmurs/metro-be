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

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class RoutesResponse {
  private Long routeId;
  private String routeName;
  private String routeCode;
  private float distanceInKm;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private boolean isDeleted;
}
