package org.alfred.ticketservice.dto.ticket;

import lombok.Builder;
import org.alfred.ticketservice.model.enums.TicketStatus;

import java.time.LocalDateTime;
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
        LocalDateTime updatedAt
) {
}
