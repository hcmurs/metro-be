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

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StationsRequest {
  @NotNull(message = "Station code cannot be null")
  private String stationCode;

  @NotNull(message = "Name cannot be null")
  private String name;

  @NotNull(message = "Address cannot be null")
  private String address;

  @NotNull(message = "Latitude cannot be null")
  private Double latitude;

  @NotNull(message = "Longitude cannot be null")
  private Double longitude;
}
