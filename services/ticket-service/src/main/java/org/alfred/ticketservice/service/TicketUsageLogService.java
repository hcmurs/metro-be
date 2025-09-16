/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Ticket-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package org.alfred.ticketservice.service;

import java.time.LocalDateTime;
import java.util.List;
import org.alfred.ticketservice.dto.ticket_usage.TicketUsageLogRequest;
import org.alfred.ticketservice.dto.ticket_usage.TicketUsageLogResponse;

public interface TicketUsageLogService {
  TicketUsageLogResponse getTicketUsageLogById(Long id);

  TicketUsageLogResponse createTicketUsageLog(TicketUsageLogRequest ticketUsageLog);

  List<TicketUsageLogResponse> getTicketUsageLogByStation(Long stationId);

  List<TicketUsageLogResponse> findAllByUsageTimeBetween(LocalDateTime start, LocalDateTime end);
}
