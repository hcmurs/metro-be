/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Ticket-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package org.alfred.ticketservice.dto.ticket;

import java.time.LocalDateTime;
import lombok.Builder;
import org.alfred.ticketservice.model.enums.TicketStatus;

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
