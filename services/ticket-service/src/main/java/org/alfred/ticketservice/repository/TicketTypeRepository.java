package org.alfred.ticketservice.repository;

import org.alfred.ticketservice.model.TicketTypes;
import org.alfred.ticketservice.model.enums.Duration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketTypeRepository extends JpaRepository<TicketTypes, Long> {
    // Custom query methods can be defined here if needed
    TicketTypes findByName(String name);
    TicketTypes findByValidityDuration(int validityDuration);
}
