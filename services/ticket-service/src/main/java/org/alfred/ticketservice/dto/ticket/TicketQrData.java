package org.alfred.ticketservice.dto.ticket;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import org.alfred.ticketservice.model.enums.TicketStatus;

import java.time.LocalDateTime;
@Builder

public record TicketQrData(
    Long ticketId,
    String ticketTypeName,
    String name,
    String validFrom,
    String validUntil,
    String ticketCode,
    float actualPrice,
    String signature
    
) {
}
