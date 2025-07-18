package com.hieunn.cronjobservice.services;

import com.hieunn.cronjobservice.models.HourUsageStatistic;
import com.hieunn.cronjobservice.models.StationUsageStatistic;
import com.hieunn.cronjobservice.models.TicketTypeStatistic;

import java.util.List;

public interface StatisticDataService {
    List<TicketTypeStatistic> findAllTicketTypeStatistics();

    List<StationUsageStatistic> findAllStationUsageStatistics();

    List<HourUsageStatistic> findAllHourUsageStatistics();
}
