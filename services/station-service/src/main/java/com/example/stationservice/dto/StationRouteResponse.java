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

@Builder
public record StationRouteResponse(
    Long id,
    Long RouteId,
    Integer sequenceOrder,
    StationsResponse stationsResponse,
    Stations.Status status,
    boolean isDeleted,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {}
