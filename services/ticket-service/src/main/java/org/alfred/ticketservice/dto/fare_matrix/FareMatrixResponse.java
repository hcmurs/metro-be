/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Ticket-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package org.alfred.ticketservice.dto.fare_matrix;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record FareMatrixResponse(
    Long fareMatrixId,
    String name,
    float distanceInKm,
    Long startStationId,
    Long endStationId,
    Float price,
    boolean isActive,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {}
