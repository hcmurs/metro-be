package org.alfred.ticketservice.service;

import jakarta.persistence.EntityNotFoundException;
import org.alfred.ticketservice.dto.ticket.TicketResponse;
import org.alfred.ticketservice.dto.ticket_usage.TicketUsageLogRequest;
import org.alfred.ticketservice.dto.ticket_usage.TicketUsageLogResponse;
import org.alfred.ticketservice.model.TicketUsageLogs;
import org.alfred.ticketservice.model.Tickets;
import org.alfred.ticketservice.model.enums.TicketStatus;
import org.alfred.ticketservice.model.enums.UsageTypes;
import org.alfred.ticketservice.repository.TicketRepository;
import org.alfred.ticketservice.repository.TicketUsageLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketUsageLogServiceImpl implements TicketUsageLogService {
    @Autowired
    TicketUsageLogRepository ticketUsageLogRepository;

    @Autowired
    TicketService ticketService;

    @Autowired
    TicketRepository ticketRepository;

    @Override
    public TicketUsageLogResponse getTicketUsageLogById(Long id) {
        TicketUsageLogs ticketUsageLog = ticketUsageLogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket usage log not found"));
        return mapToResponse(ticketUsageLog);
    }

    @Override
    public TicketUsageLogResponse createTicketUsageLog(TicketUsageLogRequest ticketUsageLog) {
        UsageTypes usageType;
        TicketResponse ticket = ticketService.getTicketByQr(ticketUsageLog.qrCodeData());
        if (ticket == null) {
            throw new EntityNotFoundException("Error: Ticket not found for the provided QR code");
        }
        Tickets ticketEntity = ticketRepository.findById(ticket.id())
                .orElseThrow(() -> new EntityNotFoundException("Ticket not found"));
        if(ticketEntity.getStatus() == TicketStatus.NOT_USED) {
            usageType = UsageTypes.ENTRY;
        } else if(ticketEntity.getStatus() == TicketStatus.USED) {
            usageType = UsageTypes.EXIT;
        } else {
            throw new IllegalStateException("Ticket is already used or invalid for usage");
        }
        TicketUsageLogs ticketUsageLogs = TicketUsageLogs.builder()
                .ticket(ticketEntity)
                .stationId(ticketUsageLog.stationId())
                .usageTime(LocalDateTime.now())
                .usageType(usageType)
                .build();
        ticketUsageLogs = ticketUsageLogRepository.save(ticketUsageLogs);
        return mapToResponse(ticketUsageLogs);
            }

    @Override
    public List<TicketUsageLogResponse> getTicketUsageLogByStation(Long stationId) {
        List<TicketUsageLogs> ticketUsageLogEntities = ticketUsageLogRepository.findByStationId(
                stationId);
        if (ticketUsageLogEntities == null) {
            throw new EntityNotFoundException("No ticket usage log found for the provided station ID");
        }
        return ticketUsageLogEntities.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<TicketUsageLogResponse> findAllByUsageTimeBetween(LocalDateTime start, LocalDateTime end) {
        List<TicketUsageLogs> ticketUsageLogs = ticketUsageLogRepository.findAllByUsageTimeBetween(start, end);
        return ticketUsageLogs
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private TicketUsageLogResponse mapToResponse(TicketUsageLogs ticketUsageLog) {
        return TicketUsageLogResponse.builder()
                .ticketUsageLogId(ticketUsageLog.getTicketUsageLogId())
                .ticketCode(ticketUsageLog.getTicket().getTicketCode())
                .stationId(ticketUsageLog.getStationId())
                .usageTime(ticketUsageLog.getUsageTime())
                .usageType(ticketUsageLog.getUsageType())
                .build();
    }
}
