package org.alfred.ticketservice.dto.ticket_usage;

import lombok.Builder;
import org.alfred.ticketservice.model.enums.UsageTypes;

import java.time.LocalDateTime;
@Builder
public record TicketUsageLogResponse(
        Long ticketUsageLogId,
        String ticketCode,
        LocalDateTime usageTime,
        Long stationId,
        UsageTypes usageType
) {
}
