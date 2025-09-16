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

import com.example.stationservice.model.BusRoutes;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Response {
  @Builder
  public record BusStationResponse(
      String id,
      String name,
      double latitude,
      double longitude,
      Integer isActive,
      String address,
      String code,
      List<BusRoutes> routes) {}
}
