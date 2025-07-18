package com.hieunn.cronjobservice.schedulers;

import com.hieunn.cronjobservice.services.StatisticsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DailyStatisticsScheduler {
    StatisticsService statisticsService;

    @Scheduled(cron = "0 0 0 * * *")
    public void runDailyStats() {
        LocalDate today = LocalDate.now().minusDays(1);
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.plusDays(1).atStartOfDay();

        statisticsService.generateDailyStatistics(start, end);
    }

//    @Scheduled(fixedDelay = Long.MAX_VALUE, initialDelay = 3000)
//    public void runHistoricalStats() {
//        log.info("Starting historical statistics generation from 2025-07-01 to 2025-07-16");
//
//        LocalDate startDate = LocalDate.of(2025, 7, 1);
//        LocalDate endDate = LocalDate.of(2025, 7, 16);
//
//        LocalDate currentDate = startDate;
//        int successCount = 0;
//        int failureCount = 0;
//
//        while (!currentDate.isAfter(endDate)) {
//            try {
//                log.info("Processing statistics for date: {}", currentDate);
//
//                LocalDateTime start = currentDate.atStartOfDay();
//                LocalDateTime end = currentDate.plusDays(1).atStartOfDay();
//
//                statisticsService.generateDailyStatistics(start, end);
//                successCount++;
//
//                log.info("Successfully processed statistics for date: {}", currentDate);
//
//                Thread.sleep(500);
//            } catch (Exception e) {
//                log.error("Failed to process statistics for date: {}", currentDate, e);
//                failureCount++;
//            }
//
//            currentDate = currentDate.plusDays(1);
//        }
//
//        log.info("Historical statistics generation completed. Success: {}, Failures: {}",
//                successCount, failureCount);
//    }
}
