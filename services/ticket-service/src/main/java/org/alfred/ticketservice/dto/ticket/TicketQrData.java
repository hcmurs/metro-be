package org.alfred.ticketservice.dto.ticket;

import lombok.Builder;
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
