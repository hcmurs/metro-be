package org.alfred.ticketservice.service;

import org.alfred.ticketservice.dto.ticket_type.TicketTypeRequest;
import org.alfred.ticketservice.dto.ticket_type.TicketTypeResponse;

import java.util.List;

public interface TicketTypeService {

    TicketTypeResponse getTicketTypeById(Long id);

    TicketTypeResponse createTicketType(TicketTypeRequest ticketType);

    TicketTypeResponse updateTicketType(TicketTypeRequest ticketType,Long id);

    List<TicketTypeResponse> getAll();

    List<TicketTypeResponse> getAllAdmin();

    void deleteTicketType(Long id);

    TicketTypeResponse getTicketTypeByTicketCode(String ticketCode);
}
