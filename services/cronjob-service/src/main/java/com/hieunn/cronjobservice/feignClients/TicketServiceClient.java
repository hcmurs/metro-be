/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Cronjob-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.hieunn.cronjobservice.feignClients;

import com.hieunn.cronjobservice.configs.FeignConfig;
import com.hieunn.cronjobservice.dtos.ApiResponse;
import com.hieunn.cronjobservice.dtos.TicketTypeDto;
import com.hieunn.cronjobservice.dtos.TicketUsageLogDto;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
    name = "ticket-service",
    url = "${service.tickets.url}",
    configuration = FeignConfig.class)
public interface TicketServiceClient {
  @GetMapping("/ticket-usage-logs/between")
  ApiResponse<List<TicketUsageLogDto>> findAllTicketUsageLogsBetween(
      @RequestParam LocalDateTime start, @RequestParam LocalDateTime end);

  @GetMapping("/ticket-types")
  ApiResponse<TicketTypeDto> findAllTicketTypes(@RequestParam String ticketCode);

  @GetMapping("/ticket-types")
  ApiResponse<List<TicketTypeDto>> findAllTicketTypes();
}
