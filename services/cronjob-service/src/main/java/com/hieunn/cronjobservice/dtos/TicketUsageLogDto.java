package com.hieunn.cronjobservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketUsageLogDto {
    Long ticketUsageLogId;
    String ticketCode;
    LocalDateTime usageTime;
    Long stationId;
    String usageType;
}
