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
        LocalDateTime updatedAt
) {}
