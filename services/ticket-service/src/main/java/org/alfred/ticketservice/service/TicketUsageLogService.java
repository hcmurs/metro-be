package org.alfred.ticketservice.service;

import org.alfred.ticketservice.dto.ticket_usage.TicketUsageLogRequest;
import org.alfred.ticketservice.dto.ticket_usage.TicketUsageLogResponse;

import java.util.List;

public interface TicketUsageLogService {
    TicketUsageLogResponse getTicketUsageLogById(Long id);
    TicketUsageLogResponse createTicketUsageLog(TicketUsageLogRequest ticketUsageLog);
    List<TicketUsageLogResponse> getTicketUsageLogByStation(Long stationId);
}
