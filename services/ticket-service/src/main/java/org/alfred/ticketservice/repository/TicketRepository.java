/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Ticket-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package org.alfred.ticketservice.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.alfred.ticketservice.model.FareMatrix;
import org.alfred.ticketservice.model.TicketTypes;
import org.alfred.ticketservice.model.Tickets;
import org.alfred.ticketservice.model.enums.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Tickets, Long> {
  // Custom query methods can be defined here if needed
  Tickets findByTicketCode(String ticketCode);

  List<Tickets> findByFareMatrix(FareMatrix fareMatrix);

  List<Tickets> findByTicketType(TicketTypes ticketType);

  List<Tickets> findByTicketIdIn(List<Long> ticketIds);

  List<Tickets> findByTicketIdInAndStatus(List<Long> ticketIds, TicketStatus status);

  List<Tickets> findByStatusAndValidUntilBefore(TicketStatus status, LocalDateTime time);
}
