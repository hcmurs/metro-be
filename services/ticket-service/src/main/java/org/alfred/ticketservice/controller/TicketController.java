package org.alfred.ticketservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alfred.ticketservice.config.ApiResponse;
import org.alfred.ticketservice.dto.ticket.TicketRequest;
import org.alfred.ticketservice.dto.ticket.TicketResponse;
import org.alfred.ticketservice.dto.ticket.TicketScanRequest;
import org.alfred.ticketservice.model.enums.TicketStatus;
import org.alfred.ticketservice.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ts/tickets")
@RequiredArgsConstructor
@Slf4j
@Validated
public class TicketController {

    @Autowired
    TicketService ticketService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TicketResponse>> getTicketById(@PathVariable Long id) {
        log.info("Request to get ticket by id: {}", id);
        TicketResponse ticket = ticketService.getTicketById(id);
        return ResponseEntity.ok(ApiResponse.success(ticket, "Ticket retrieved successfully"));
    }

    @PostMapping("/ticket-type")
    public ResponseEntity<ApiResponse<TicketResponse>> createTicketType(@Valid @RequestBody TicketRequest.TicketType request) {
        log.info("Request to create ticket for type ID: {}",
                request.id());
        TicketResponse createdTicket = ticketService.createTicketType(request);
        return new ResponseEntity<>(ApiResponse.success(createdTicket, "Ticket created successfully"),
                HttpStatus.CREATED);
    }

    @PostMapping("/fare-matrix")
    public ResponseEntity<ApiResponse<TicketResponse>> createTicketFare(@Valid @RequestBody TicketRequest.FareMatrix request) {
        log.info("Request to create ticket for type ID: {}",
                request.id());
        TicketResponse createdTicket = ticketService.createTicketFare(request);
        return new ResponseEntity<>(ApiResponse.success(createdTicket, "Ticket created successfully"),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<TicketResponse>> updateTicketStatus(
            @PathVariable Long id,
            @RequestParam TicketStatus status) {
        log.info("Request to update ticket status: ID={}, status={}", id, status);
        TicketResponse updatedTicket = ticketService.updateTicket(status, id);
        return ResponseEntity.ok(ApiResponse.success(updatedTicket, "Ticket updated successfully"));
    }

    @GetMapping("/code/{ticketCode}")
    public ResponseEntity<ApiResponse<TicketResponse>> getTicketByCode(
            @PathVariable @NotBlank String ticketCode) {
        log.info("Request to get ticket by code: {}", ticketCode);
        TicketResponse ticket = ticketService.getTicketByCode(ticketCode);
        return ResponseEntity.ok(ApiResponse.success(ticket, "Ticket retrieved successfully"));
    }

//    @GetMapping("/qr")
//    public ResponseEntity<ApiResponse<TicketResponse>> getTicketByQr(
//            @RequestParam @NotBlank String qrData) {
//        log.info("Request to get ticket by QR data");
//        TicketResponse ticket = ticketService.getTicketByQr(qrData);
//        return ResponseEntity.ok(ApiResponse.success(ticket, "Ticket retrieved successfully"));
//    }

    @GetMapping("/fare-matrix/{fareMatrixId}")
    public ResponseEntity<ApiResponse<List<TicketResponse>>> getTicketsByFareMatrix(
            @PathVariable Long fareMatrixId) {
        log.info("Request to get tickets by fare matrix ID: {}", fareMatrixId);
        List<TicketResponse> tickets = ticketService.getTicketByFareMatrixId(fareMatrixId);
        return ResponseEntity.ok(ApiResponse.success(tickets, "Tickets retrieved successfully"));
    }

    @GetMapping("/ticket-type/{ticketTypeId}")
    public ResponseEntity<ApiResponse<List<TicketResponse>>> getTicketsByTicketType(
            @PathVariable Long ticketTypeId) {
        log.info("Request to get tickets by ticket type ID: {}", ticketTypeId);
        List<TicketResponse> tickets = ticketService.getTicketsByTicketType(ticketTypeId);
        return ResponseEntity.ok(ApiResponse.success(tickets, "Tickets retrieved successfully"));
    }

    @PostMapping("/scan/entry")
    public ResponseEntity<ApiResponse<TicketResponse>> scanEntryTicket(
            @Valid @RequestBody TicketScanRequest request) {
        log.info("Request to scan entry ticket at station ID: {}", request.stationId());
        TicketResponse scannedTicket = ticketService.scanEntryTicket(request);
        return ResponseEntity.ok(ApiResponse.success(scannedTicket, "Ticket entry processed successfully"));
    }

    @PostMapping("/scan/exit")
    public ResponseEntity<ApiResponse<TicketResponse>> scanExitTicket(
            @Valid @RequestBody TicketScanRequest request) {
        log.info("Request to scan exit ticket at station ID: {}", request.stationId());
        TicketResponse scannedTicket = ticketService.scanExitTicket(request);
        return ResponseEntity.ok(ApiResponse.success(scannedTicket, "Ticket exit processed successfully"));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<TicketResponse>> cancelTicket(@PathVariable Long id) {
        log.info("Request to cancel ticket with ID: {}", id);
        TicketResponse cancelledTicket = ticketService.cancel(id);
        return ResponseEntity.ok(ApiResponse.success(cancelledTicket, "Ticket cancelled successfully"));
    }

    @GetMapping("/generate-qr")
    public ResponseEntity<ApiResponse<byte[]>> generateQrCodeData(@RequestParam @NotBlank String ticketCode) {
        log.info("Request to generate QR code data for: {}", ticketCode);
        byte[] qrCodeData = ticketService.generateQrCodeData(ticketCode);
        return ResponseEntity.ok(ApiResponse.success(qrCodeData, "QR code data generated successfully"));
    }
}