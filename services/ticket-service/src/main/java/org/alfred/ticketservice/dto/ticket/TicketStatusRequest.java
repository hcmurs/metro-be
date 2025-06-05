package org.alfred.ticketservice.dto.ticket;

import org.alfred.ticketservice.model.enums.TicketStatus;

public record TicketStatusRequest(
        TicketStatus status
) {
}
