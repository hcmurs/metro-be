package org.alfred.ticketservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alfred.ticketservice.config.ApiResponse;
import org.alfred.ticketservice.dto.ticket_type.TicketTypeRequest;
import org.alfred.ticketservice.dto.ticket_type.TicketTypeResponse;
import org.alfred.ticketservice.service.TicketTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ts/ticket-types")
@RequiredArgsConstructor
@Slf4j
@Validated
public class TicketTypeController {

    private final TicketTypeService ticketTypeService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TicketTypeResponse>> getTicketTypeById(@PathVariable Long id) {
        log.info("Request to get ticket type by id: {}", id);
        TicketTypeResponse ticketType = ticketTypeService.getTicketTypeById(id);
        return ResponseEntity.ok(ApiResponse.success(ticketType, "Ticket type retrieved successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllTicketTypes(
            @RequestParam(required = false) String ticketCode) {
        log.info("Request to get all ticket types");

        if (ticketCode != null && !ticketCode.isEmpty()) {
            TicketTypeResponse ticketType = ticketTypeService.getTicketTypeByTicketCode(ticketCode);
            return ResponseEntity.ok(ApiResponse.success(ticketType, "Ticket types retrieved successfully"));
        }
        List<TicketTypeResponse> ticketTypes = ticketTypeService.getAll();
        return ResponseEntity.ok(ApiResponse.success(ticketTypes, "Ticket types retrieved successfully"));
    }


    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<TicketTypeResponse>> createTicketType(@Valid @RequestBody TicketTypeRequest request) {
        log.info("Request to create ticket type with name: {}", request.name());
        TicketTypeResponse createdTicketType = ticketTypeService.createTicketType(request);
        return new ResponseEntity<>(
                ApiResponse.success(createdTicketType, "Ticket type created successfully"),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<TicketTypeResponse>> updateTicketType(@Valid @RequestBody TicketTypeRequest request,
                                                                           @PathVariable Long id)  {
        log.info("Request to update ticket type with ID: {}", id);
        TicketTypeResponse updatedTicketType = ticketTypeService.updateTicketType(request,id);
        return ResponseEntity.ok(ApiResponse.success(updatedTicketType, "Ticket type updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteTicketType(@PathVariable Long id) {
        log.info("Request to delete (deactivate) ticket type with ID: {}", id);
        ticketTypeService.deleteTicketType(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Ticket type deactivated successfully"));
    }
}