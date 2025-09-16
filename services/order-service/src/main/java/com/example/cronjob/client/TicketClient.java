/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Order-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.example.cronjob.client;

import com.example.cronjob.Config.FeignConfig;
import com.example.cronjob.DTO.Request.TicketRequest;
import com.example.cronjob.DTO.Response.ApiResponse;
import com.example.cronjob.DTO.Response.TicketResponse;
import com.example.cronjob.Enum.TicketStatus;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
    name = "ticket-service",
    url = "${service.users.url}",
    configuration = FeignConfig.class)
public interface TicketClient {
  @PostMapping("/ticket-type")
  ApiResponse<TicketResponse> createTicketType(@RequestBody TicketRequest.TicketType ticket);

  @PostMapping("/fare-matrix")
  ApiResponse<TicketResponse> createTicketFare(@RequestBody TicketRequest.FareMatrix fareMatrix);

  @PutMapping("/{id}/status")
  ApiResponse<TicketResponse> updateTicketStatus(
      @PathVariable Long id, @RequestParam TicketStatus status);

  @GetMapping("/{id}")
  ApiResponse<TicketResponse> getTicketById(@PathVariable Long id);

  @GetMapping("/batch")
  ApiResponse<List<TicketResponse>> getTicketsByIds(
      @RequestParam("ticketIds") List<Long> ticketIds);

  @GetMapping("/get-by-status")
  ApiResponse<List<TicketResponse>> getTicketsByStatus(
      @RequestParam List<Long> ticketIds, @RequestParam TicketStatus status);
}
