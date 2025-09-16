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

import com.example.stationservice.model.Stations;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StationsResponse {
  private Long stationId;
  private String stationCode;
  private String name;
  private String address;
  private Double latitude;
  private Double longitude;
  private boolean isDeleted;
  private Stations.Status status;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
