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

import org.alfred.ticketservice.dto.StationEvent;

public interface TicketReactionService {
  void handleDeleteStation(StationEvent event);

  void handleAddStation(StationEvent event);
}
