package org.alfred.ticketservice.repository;

import org.alfred.ticketservice.model.TicketUpgradeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketUpgradeHistoryRepository extends JpaRepository<TicketUpgradeHistory,Long> {
}
