package org.alfred.ticketservice.service;

import jakarta.persistence.EntityNotFoundException;
import org.alfred.ticketservice.dto.ticket_type.TicketTypeRequest;
import org.alfred.ticketservice.dto.ticket_type.TicketTypeResponse;
import org.alfred.ticketservice.model.TicketTypes;
import org.alfred.ticketservice.model.Tickets;
import org.alfred.ticketservice.repository.TicketRepository;
import org.alfred.ticketservice.repository.TicketTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TicketTypeServiceImpl implements TicketTypeService{
    @Autowired
    TicketTypeRepository ticketTypeRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Override
    public TicketTypeResponse getTicketTypeById(Long id) {
        TicketTypes ticketTypes = ticketTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket type not found"));
        return mapToResponse(ticketTypes);
    }

    @Override
    public TicketTypeResponse createTicketType(TicketTypeRequest ticketType) {
        if (ticketTypeRepository.findByName(ticketType.name()) != null) {
            throw new IllegalArgumentException("Ticket type with this name already exists");
        }
        if (ticketTypeRepository.findByValidityDuration(ticketType.validityDuration()) != null) {
            throw new IllegalArgumentException("Ticket type with this validity duration already exists");
        }
        TicketTypes ticketTypes = TicketTypes.builder()
                .name(ticketType.name())
                .description(ticketType.description())
                .price(ticketType.price())
                .validityDuration(ticketType.validityDuration())
                .isActive(ticketType.isActive())
                .build();
        ticketTypes = ticketTypeRepository.save(ticketTypes);
        return mapToResponse(ticketTypes);
    }

    @Override
    public TicketTypeResponse updateTicketType(TicketTypeRequest ticketType, Long id) {
        TicketTypes ticketTypes = ticketTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket type not found"));
        ticketTypes.setName(ticketType.name());
        ticketTypes.setDescription(ticketType.description());
        ticketTypes.setPrice(ticketType.price());
        ticketTypes.setValidityDuration(ticketType.validityDuration());
        ticketTypes.setActive(ticketType.isActive());
        ticketTypes = ticketTypeRepository.save(ticketTypes);
        return mapToResponse(ticketTypes);
    }

    @Override
    public List<TicketTypeResponse> getAll() {
        return ticketTypeRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<TicketTypeResponse> getAllAdmin() {
        return ticketTypeRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void deleteTicketType(Long id) {
        TicketTypes ticketTypes = ticketTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket type not found"));
        ticketTypes.setActive(false);
        ticketTypeRepository.save(ticketTypes);
    }

    @Override
    public TicketTypeResponse getTicketTypeByTicketCode(String ticketCode) {
        Tickets ticket = ticketRepository.findByTicketCode(ticketCode);
        return mapToResponse(ticket.getTicketType());
    }

    private TicketTypeResponse mapToResponse(TicketTypes ticketTypes) {
        return TicketTypeResponse.builder()
                .id(ticketTypes.getTicketTypeId())
                .name(ticketTypes.getName())
                .description(ticketTypes.getDescription())
                .price(ticketTypes.getPrice())
                .validityDuration(ticketTypes.getValidityDuration())
                .forStudent(ticketTypes.isForStudent())
                .isActive(ticketTypes.isActive())
                .createdAt(ticketTypes.getCreatedAt())
                .updatedAt(ticketTypes.getUpdatedAt())
                .build();
    }
}
