package org.alfred.ticketservice.repository;

import org.alfred.ticketservice.model.TicketUsageLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TicketUsageLogRepository extends JpaRepository<TicketUsageLogs, Long> {
    // Custom query methods can be defined here if needed
//    TicketUsageLogs findByQrCodeData(String qrCodeData);
    List<TicketUsageLogs> findByStationId(Long stationId);
    List<TicketUsageLogs> findByStationIdAndUsageType(Long stationId, String usageType);
    List<TicketUsageLogs> findAllByUsageTimeBetween(LocalDateTime start, LocalDateTime end);
}
