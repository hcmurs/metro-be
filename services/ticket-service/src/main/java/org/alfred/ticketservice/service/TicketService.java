/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Ticket-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package org.alfred.ticketservice.service;


import org.alfred.ticketservice.dto.ticket.TicketRequest;
import org.alfred.ticketservice.dto.ticket.TicketResponse;
import org.alfred.ticketservice.dto.ticket.TicketScanRequest;
import org.alfred.ticketservice.model.enums.TicketStatus;

import java.util.List;

public interface TicketService {
    TicketResponse getTicketById(Long ticketId);
    TicketResponse createTicketType(TicketRequest.TicketType ticket);
    TicketResponse createTicketFare(TicketRequest.FareMatrix ticket);
    TicketResponse updateTicket(TicketStatus status, Long id);
    TicketResponse getTicketByCode(String code);
    TicketResponse getTicketByQr(String code);
    List<TicketResponse> getTicketByFareMatrixId(Long fareMatrixId);
    TicketResponse scanEntryTicket(TicketScanRequest ticketScanRequest);
    TicketResponse scanExitTicket(TicketScanRequest ticketScanRequest);
    TicketResponse cancel(Long ticketId);
    List<TicketResponse> getTicketsByTicketType(Long ticketTypeId);
    byte[] generateQrCodeData(String ticketCode);
    List<TicketResponse> getTicketsByIds(List<Long> ticketIds);
    List<TicketResponse> getTicketsByIdsAndStatus(List<Long> ticketIds, TicketStatus status);
    TicketResponse upgradeTicket(Long ticketId, Long endStationId);
    double getUpgradeAmount(Long ticketId, Long endStationId);
    List<StationRouteResponse> getStationForUpgradeTicket(Long ticketId);
}
