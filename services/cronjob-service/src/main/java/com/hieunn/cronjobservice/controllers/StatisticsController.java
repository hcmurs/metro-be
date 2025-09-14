/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.hieunn.cronjobservice.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hieunn.cronjobservice.dtos.ApiResponse;
import com.hieunn.cronjobservice.models.HourUsageStatistic;
import com.hieunn.cronjobservice.models.StationUsageStatistic;
import com.hieunn.cronjobservice.models.TicketTypeStatistic;
import com.hieunn.cronjobservice.services.StatisticDataService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/stat")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class StatisticsController {
  StatisticDataService statisticDataService;

  @GetMapping("/ticket-types")
  public ResponseEntity<ApiResponse<List<TicketTypeStatistic>>> findAllTicketTypeStatistics() {
    return ResponseEntity.ok(
        ApiResponse.success(statisticDataService.findAllTicketTypeStatistics()));
  }

  @GetMapping("/stations")
  public ResponseEntity<ApiResponse<List<StationUsageStatistic>>> findAllStationUsageStatistics() {
    return ResponseEntity.ok(
        ApiResponse.success(statisticDataService.findAllStationUsageStatistics()));
  }

  @GetMapping("/hours")
  public ResponseEntity<ApiResponse<List<HourUsageStatistic>>> findAllHourUsageStatistics() {
    return ResponseEntity.ok(
        ApiResponse.success(statisticDataService.findAllHourUsageStatistics()));
  }
}
