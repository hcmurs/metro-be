package com.example.cronjob.Service;

import com.example.cronjob.DTO.Response.DailyAnalyticsResponse;
import com.example.cronjob.Mapping.DailyAnalyticsMapping;
import com.example.cronjob.Pojos.DailyAnalytics;
import com.example.cronjob.Repository.DailyAnalyticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DailyAnalyticsServiceImpl implements DailyAnalyticsService {
    @Autowired
    private DailyAnalyticsRepository dailyAnalyticsRepository;
    @Autowired
    private DailyAnalyticsMapping dailyAnalyticsMapping;


    @Override
    public List<DailyAnalyticsResponse> getDaily() {
        List<DailyAnalytics> res = dailyAnalyticsRepository.findAll();
        return dailyAnalyticsMapping.toResponseList(res);
    }
}
