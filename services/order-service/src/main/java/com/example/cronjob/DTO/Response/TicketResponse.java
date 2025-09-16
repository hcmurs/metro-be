/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Order-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.example.cronjob.DTO.Response;

import com.example.cronjob.Enum.TicketStatus;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record TicketResponse(
    Long id,
    Long fareMatrixId,
    Long ticketTypeId,
    String name,
    String ticketCode,
    float actualPrice,
    LocalDateTime validFrom,
    LocalDateTime validUntil,
    TicketStatus status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {}
