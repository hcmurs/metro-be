/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Ticket-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package org.alfred.ticketservice.dto.ticket_type;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record TicketTypeResponse(
    Long id,
    String name,
    String description,
    float price,
    int validityDuration,
    boolean isActive,
    boolean forStudent,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {}
