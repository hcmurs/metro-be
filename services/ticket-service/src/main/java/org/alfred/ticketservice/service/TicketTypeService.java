package org.alfred.ticketservice.service;

import java.util.List;
import org.alfred.ticketservice.dto.ticket_type.TicketTypeRequest;
import org.alfred.ticketservice.dto.ticket_type.TicketTypeResponse;

public interface TicketTypeService {

    TicketTypeResponse getTicketTypeById(Long id);

    TicketTypeResponse createTicketType(TicketTypeRequest ticketType);

    TicketTypeResponse updateTicketType(TicketTypeRequest ticketType,Long id);

    List<TicketTypeResponse> getAll();

    List<TicketTypeResponse> getAllAdmin();

    void deleteTicketType(Long id);
}
