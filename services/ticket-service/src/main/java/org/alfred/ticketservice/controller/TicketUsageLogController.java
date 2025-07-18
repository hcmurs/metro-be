package org.alfred.ticketservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alfred.ticketservice.config.ApiResponse;
import org.alfred.ticketservice.dto.ticket_usage.TicketUsageLogResponse;
import org.alfred.ticketservice.service.TicketUsageLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/ts/ticket-usage-logs")
@RequiredArgsConstructor
@Slf4j
@Validated
public class TicketUsageLogController {

    private final TicketUsageLogService ticketUsageLogService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<TicketUsageLogResponse>> getTicketUsageLogById(@PathVariable Long id) {
        log.info("Request to get ticket usage log by id: {}", id);
        TicketUsageLogResponse usageLog = ticketUsageLogService.getTicketUsageLogById(id);
        return ResponseEntity.ok(ApiResponse.success(usageLog, "Ticket usage log retrieved successfully"));
    }

//    @PostMapping
//    public ResponseEntity<ApiResponse<TicketUsageLogResponse>> createTicketUsageLog(
//            @Valid @RequestBody TicketUsageLogRequest request) {
//        log.info("Request to create ticket usage log for station ID: {}", request.stationId());
//        TicketUsageLogResponse createdLog = ticketUsageLogService.createTicketUsageLog(request);
//        return new ResponseEntity<>(
//                ApiResponse.success(createdLog, "Ticket usage log created successfully"),
//                HttpStatus.CREATED
//        );
//    }

    @GetMapping("/station/{stationId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<List<TicketUsageLogResponse>>> getTicketUsageLogsByStation(
            @PathVariable Long stationId) {
        log.info("Request to get ticket usage logs for station ID: {}", stationId);
        List<TicketUsageLogResponse> usageLogs = ticketUsageLogService.getTicketUsageLogByStation(stationId);
        return ResponseEntity.ok(ApiResponse.success(usageLogs, "Ticket usage logs retrieved successfully"));
    }

    @GetMapping("/between")
    public ResponseEntity<ApiResponse<List<TicketUsageLogResponse>>> getAllTicketUsageLogs(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {
        List<TicketUsageLogResponse> usageLogs = ticketUsageLogService.findAllByUsageTimeBetween(start, end);
        return ResponseEntity.ok(ApiResponse.success(usageLogs, "Ticket usage logs retrieved successfully"));
    }
}