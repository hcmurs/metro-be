package org.alfred.ticketservice.service;

import org.alfred.ticketservice.dto.StationEvent;

public interface TicketReactionService {
    void handleDeleteStation(StationEvent event);
    void handleAddStation(StationEvent event);
}
