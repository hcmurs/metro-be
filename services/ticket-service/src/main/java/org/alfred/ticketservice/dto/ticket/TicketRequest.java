package org.alfred.ticketservice.dto.ticket;

import jakarta.validation.constraints.*;

public record TicketRequest(
        @Positive @NotNull(message = "Fare matrix ID is required") Long fareMatrixId,
        @Positive @NotNull(message = "Ticket Type ID is required") Long ticketTypeId
) {
}
