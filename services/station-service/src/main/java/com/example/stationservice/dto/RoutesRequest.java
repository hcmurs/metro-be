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

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutesRequest {
  @NotNull(message = "Route name cannot be null")
  private String routeName;

  @NotNull(message = "Route code cannot be null")
  private String routeCode;

  @NotNull(message = "Distance in km cannot be null")
  @DecimalMax(value = "10000.0", message = "DistanceInKm cannot exceed 10,000")
  @Positive(message = "DistanceInKm must be  positive")
  private float distanceInKm;
}
