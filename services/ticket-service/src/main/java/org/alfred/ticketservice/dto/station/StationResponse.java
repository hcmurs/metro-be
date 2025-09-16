/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Ticket-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package org.alfred.ticketservice.dto.station;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record StationResponse(
    Long stationId,
    String stationCode,
    String name,
    String address,
    Double latitude,
    Double longitude,
    boolean deleted,
    String status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {}
