package com.example.stationservice.service;

import com.example.stationservice.dto.SchedulesRequest;
import com.example.stationservice.model.Schedules;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SchedulesService {
    Schedules createSchedule(SchedulesRequest request);
    List<Schedules> getAllSchedules();
    Optional<Schedules> getScheduleById(Long id);
    List<Schedules> getSchedulesByStationId(Long stationId);
    Schedules updateSchedule(Long id, Schedules schedule);
    void deleteSchedule(Long id);
    boolean existsById(Long id);

}
