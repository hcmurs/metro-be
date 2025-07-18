package com.hieunn.cronjobservice.feignClients;

import com.hieunn.cronjobservice.configs.FeignConfig;
import com.hieunn.cronjobservice.dtos.ApiResponse;
import com.hieunn.cronjobservice.dtos.TicketTypeDto;
import com.hieunn.cronjobservice.dtos.TicketUsageLogDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@FeignClient(name = "ticket-service", url = "${service.tickets.url}", configuration = FeignConfig.class)
public interface TicketServiceClient {
    @GetMapping("/ticket-usage-logs/between")
    ApiResponse<List<TicketUsageLogDto>> findAllTicketUsageLogsBetween(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end);

    @GetMapping("/ticket-types")
    ApiResponse<TicketTypeDto> findAllTicketTypes(@RequestParam String ticketCode);

    @GetMapping("/ticket-types")
    ApiResponse<List<TicketTypeDto>> findAllTicketTypes();
}
