/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Cronjob-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.hieunn.cronjobservice.services;

import com.hieunn.cronjobservice.models.HourUsageStatistic;
import com.hieunn.cronjobservice.models.StationUsageStatistic;
import com.hieunn.cronjobservice.models.TicketTypeStatistic;
import com.hieunn.cronjobservice.repositories.HourUsageStatRepository;
import com.hieunn.cronjobservice.repositories.StationUsageStatRepository;
import com.hieunn.cronjobservice.repositories.TicketTypeStatRepository;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class StatisticDataServiceImpl implements StatisticDataService {
  HourUsageStatRepository hourUsageStatRepository;
  StationUsageStatRepository stationUsageStatRepository;
  TicketTypeStatRepository ticketTypeStatRepository;

  @Override
  public List<TicketTypeStatistic> findAllTicketTypeStatistics() {
    return ticketTypeStatRepository.findAll();
  }

  @Override
  public List<StationUsageStatistic> findAllStationUsageStatistics() {
    return stationUsageStatRepository.findAll();
  }

  @Override
  public List<HourUsageStatistic> findAllHourUsageStatistics() {
    return hourUsageStatRepository.findAll();
  }
}
