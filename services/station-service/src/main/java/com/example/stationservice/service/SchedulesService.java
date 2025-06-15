package com.example.stationservice.service;

import com.example.stationservice.dto.SchedulesRequest;
import com.example.stationservice.dto.SchedulesResponse;
import com.example.stationservice.model.Schedules;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SchedulesService {
    SchedulesResponse createSchedule(SchedulesRequest request);
    List<SchedulesResponse> getAllSchedules();
    Optional<SchedulesResponse> getScheduleById(Long id);
    List<SchedulesResponse> getSchedulesByStationId(Long stationId);
    SchedulesResponse updateSchedule(Long id, SchedulesRequest schedule);
    void deleteSchedule(Long id);
    boolean existsById(Long id);

}
