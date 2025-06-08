package com.example.cronjob.Mapping;

import com.example.cronjob.DTO.Response.DailyAnalyticsResponse;
import com.example.cronjob.Pojos.DailyAnalytics;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DailyAnalyticsMapping {
    @Mapping(source = "id", target = "analyticsId")
    DailyAnalyticsResponse toResponse (DailyAnalytics dailyAnalytics);
    List<DailyAnalyticsResponse> toResponseList (List<DailyAnalytics> dailyAnalytics);
}
