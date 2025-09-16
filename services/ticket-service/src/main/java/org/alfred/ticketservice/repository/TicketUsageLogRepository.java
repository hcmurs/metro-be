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
import org.alfred.ticketservice.model.TicketUsageLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketUsageLogRepository extends JpaRepository<TicketUsageLogs, Long> {
  // Custom query methods can be defined here if needed
  //    TicketUsageLogs findByQrCodeData(String qrCodeData);
  List<TicketUsageLogs> findByStationId(Long stationId);

  List<TicketUsageLogs> findByStationIdAndUsageType(Long stationId, String usageType);

  List<TicketUsageLogs> findAllByUsageTimeBetween(LocalDateTime start, LocalDateTime end);
}
