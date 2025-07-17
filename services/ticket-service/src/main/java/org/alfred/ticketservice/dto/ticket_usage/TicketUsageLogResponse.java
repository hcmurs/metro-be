package org.alfred.ticketservice.dto.ticket_usage;

import java.time.LocalDateTime;
import lombok.Builder;
import org.alfred.ticketservice.model.enums.UsageTypes;
@Builder
public record TicketUsageLogResponse(
        Long ticketUsageLogId,
        String ticketCode,
        LocalDateTime usageTime,
        Long stationId,
        UsageTypes usageType
) {
}
