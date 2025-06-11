package com.example.cronjob.DTO.Response;

import lombok.Builder;
import com.example.cronjob.Enum.TicketStatus;

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
