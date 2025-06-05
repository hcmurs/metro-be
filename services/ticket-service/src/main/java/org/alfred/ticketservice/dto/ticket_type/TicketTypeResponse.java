package org.alfred.ticketservice.dto.ticket_type;

import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record TicketTypeResponse(
        Long id,
        String name,
        String description,
        float price,
        int validityDuration,
        boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
