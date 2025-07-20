package com.hieunn.cronjobservice.services;

import java.time.LocalDateTime;

public interface StatisticsService {
    void generateDailyStatistics(LocalDateTime start, LocalDateTime end);
}
