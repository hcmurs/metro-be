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

import java.util.List;
import org.alfred.ticketservice.dto.ticket_type.TicketTypeRequest;
import org.alfred.ticketservice.dto.ticket_type.TicketTypeResponse;

public interface TicketTypeService {

  TicketTypeResponse getTicketTypeById(Long id);

  TicketTypeResponse createTicketType(TicketTypeRequest ticketType);

  TicketTypeResponse updateTicketType(TicketTypeRequest ticketType, Long id);

  List<TicketTypeResponse> getAll();

  List<TicketTypeResponse> getAllAdmin();

  void deleteTicketType(Long id);

  TicketTypeResponse getTicketTypeByTicketCode(String ticketCode);
}
