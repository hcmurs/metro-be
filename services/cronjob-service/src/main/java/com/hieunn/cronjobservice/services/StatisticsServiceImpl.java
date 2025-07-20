package com.hieunn.cronjobservice.services;

import com.hieunn.cronjobservice.dtos.StationDto;
import com.hieunn.cronjobservice.dtos.TicketTypeDto;
import com.hieunn.cronjobservice.dtos.TicketUsageLogDto;
import com.hieunn.cronjobservice.feignClients.StationServiceClient;
import com.hieunn.cronjobservice.feignClients.TicketServiceClient;
import com.hieunn.cronjobservice.models.HourUsageStatistic;
import com.hieunn.cronjobservice.models.StationUsageStatistic;
import com.hieunn.cronjobservice.models.TicketTypeStatistic;
import com.hieunn.cronjobservice.repositories.HourUsageStatRepository;
import com.hieunn.cronjobservice.repositories.StationUsageStatRepository;
import com.hieunn.cronjobservice.repositories.TicketTypeStatRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {
    StationUsageStatRepository stationStatRepo;
    TicketTypeStatRepository ticketTypeStatRepo;
    HourUsageStatRepository peakHourStatRepo;

    TicketServiceClient ticketServiceClient;
    StationServiceClient stationServiceClient;

    @Transactional
    @Override
    public void generateDailyStatistics(LocalDateTime start, LocalDateTime end) {
        try {
            List<TicketUsageLogDto> logs = ticketServiceClient.findAllTicketUsageLogsBetween(start, end).getData();

            if (logs == null || logs.isEmpty()) {
                return;
            }

            generateStationUsageStatistics(logs, start);
            generateTicketTypeStatistics(logs, start);
            generatePeakHourStatistics(logs, start);

        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Tạo thống kê số lượt quét tại mỗi ga
     */
    private void generateStationUsageStatistics(List<TicketUsageLogDto> logs, LocalDateTime start) {
        try {
            final Map<Long, String> stationNameMap = findAllStation().stream()
                    .collect(Collectors.toMap(StationDto::getStationId, StationDto::getName));

            Map<Long, StationUsageStatistic> stationStats = new HashMap<>();

            for (TicketUsageLogDto log : logs) {
                Long stationId = log.getStationId();
                StationUsageStatistic stat = stationStats.computeIfAbsent(stationId, id ->
                        StationUsageStatistic.builder()
                                .stationId(id)
                                .usageDate(start.toLocalDate())
                                .stationName(stationNameMap.get(id))
                                .entryCount(0)
                                .exitCount(0)
                                .build()
                );

                if ("ENTRY".equalsIgnoreCase(log.getUsageType())) {
                    stat.setEntryCount(stat.getEntryCount() + 1);
                } else {
                    stat.setExitCount(stat.getExitCount() + 1);
                }
            }

            stationStatRepo.saveAll(stationStats.values());
        } catch (Exception e) {
            log.error("Error generating station usage statistics for date: {}", start.toLocalDate(), e);
        }
    }

    /**
     * Tạo thống kê loại vé được sử dụng
     */
    private void generateTicketTypeStatistics(List<TicketUsageLogDto> logs, LocalDateTime start) {
        try {
            Map<String, String> ticketCodeToTypeMap = new HashMap<>();

            Map<String, Long> ticketTypeCounts = logs.stream()
                    .map(log -> {
                        String ticketCode = log.getTicketCode();
                        return ticketCodeToTypeMap.computeIfAbsent(ticketCode, code -> {
                            TicketTypeDto dto = findTicketTypeByTicketCode(code);
                            return dto != null ? dto.getName() : "UNKNOWN";
                        });
                    })
                    .collect(Collectors.groupingBy(type -> type, Collectors.counting()));

            List<TicketTypeStatistic> ticketTypeStats = ticketTypeCounts.entrySet().stream()
                    .map(entry -> TicketTypeStatistic.builder()
                            .ticketType(entry.getKey())
                            .usageDate(start.toLocalDate())
                            .usageCount(entry.getValue().intValue())
                            .build())
                    .collect(Collectors.toList());

            ticketTypeStatRepo.saveAll(ticketTypeStats);
        } catch (Exception e) {
            log.error("Error generating ticket type statistics for date: {}", start.toLocalDate(), e);
        }
    }

    /**
     * Tạo thống kê số lượt quét vé tại mỗi khung giờ trong ngày
     */
    private void generatePeakHourStatistics(List<TicketUsageLogDto> logs, LocalDateTime start) {
        try {
            Map<Integer, HourUsageStatistic> hourStats = new HashMap<>();

            for (TicketUsageLogDto log : logs) {
                int hour = log.getUsageTime().getHour();

                HourUsageStatistic stat = hourStats.computeIfAbsent(hour, h ->
                        HourUsageStatistic.builder()
                                .usageDate(start.toLocalDate())
                                .startHour(h)
                                .endHour(h + 1)
                                .entryCount(0)
                                .exitCount(0)
                                .build()
                );

                if ("ENTRY".equalsIgnoreCase(log.getUsageType())) {
                    stat.setEntryCount(stat.getEntryCount() + 1);
                } else {
                    stat.setExitCount(stat.getExitCount() + 1);
                }
            }

            peakHourStatRepo.saveAll(hourStats.values());
        } catch (Exception e) {
            log.error("Error generating peak hour statistics for date: {}", start.toLocalDate(), e);
        }
    }

    private List<StationDto> findAllStation() {
        return stationServiceClient.findAllStations().getData();
    }

    private TicketTypeDto findTicketTypeByTicketCode(String ticketCode) {
        return ticketServiceClient.findAllTicketTypes(ticketCode).getData();
    }
}