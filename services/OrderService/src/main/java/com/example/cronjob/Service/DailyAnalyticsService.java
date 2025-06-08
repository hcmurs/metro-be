package com.example.cronjob.Service;

import com.example.cronjob.DTO.Response.DailyAnalyticsResponse;

import java.util.List;

public interface DailyAnalyticsService {
    List<DailyAnalyticsResponse> getDaily();
}
