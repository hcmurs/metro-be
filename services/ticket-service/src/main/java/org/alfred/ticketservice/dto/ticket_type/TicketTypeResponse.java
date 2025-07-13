package org.alfred.ticketservice.dto.ticket_type;

import lombok.Builder;
import org.alfred.ticketservice.model.enums.Duration;

import java.time.LocalDateTime;
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
        LocalDateTime updatedAt
) {}
