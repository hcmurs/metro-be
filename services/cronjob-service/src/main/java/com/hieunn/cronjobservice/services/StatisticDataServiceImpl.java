package com.hieunn.cronjobservice.services;

import com.hieunn.cronjobservice.models.HourUsageStatistic;
import com.hieunn.cronjobservice.models.StationUsageStatistic;
import com.hieunn.cronjobservice.models.TicketTypeStatistic;
import com.hieunn.cronjobservice.repositories.HourUsageStatRepository;
import com.hieunn.cronjobservice.repositories.StationUsageStatRepository;
import com.hieunn.cronjobservice.repositories.TicketTypeStatRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

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
