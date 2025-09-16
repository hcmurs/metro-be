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
import jakarta.validation.constraints.Positive;

public record StationRouteRequest(
    @NotNull(message = "Route ID is required") Long routeId,
    @NotNull(message = "Sequence order is required")
        @Positive(message = "Sequence order must be positive")
        Integer sequenceOrder,
    @NotNull(message = "Station ID is required") Long stationId) {}
