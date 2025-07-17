package org.alfred.ticketservice.repository;

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
}
