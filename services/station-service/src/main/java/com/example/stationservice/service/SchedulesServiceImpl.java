package com.example.stationservice.service;

import com.example.stationservice.dto.SchedulesRequest;
import com.example.stationservice.model.Schedules;
import com.example.stationservice.model.Stations;
import com.example.stationservice.repository.RoutesRepository;
import com.example.stationservice.repository.SchedulesRepository;
import com.example.stationservice.repository.StationsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class SchedulesServiceImpl implements SchedulesService {
    @Autowired
    private SchedulesRepository schedulesRepository;

    @Autowired
    private StationsRepository stationsRepository;


    @Override
    public Schedules createSchedule(SchedulesRequest request) {
        Stations station = stationsRepository.findById(request.getStationId())
                .orElseThrow(() -> new RuntimeException("Station not found with id: " + request.getStationId()));
        if (request.getTimeArrival() != null && request.getTimeDeparture() != null &&
                request.getTimeArrival().isAfter(request.getTimeDeparture())) {
            throw new RuntimeException("Arrival time cannot be after departure time");
        }
        Schedules schedule = new Schedules();
        schedule.setDescription(request.getDescription());
        schedule.setTimeArrival(request.getTimeArrival());
        schedule.setTimeDeparture(request.getTimeDeparture());
        schedule.setStation(station);
        schedule.setDirection(request.getDirection());
        schedule.setCreatedAt(LocalDateTime.now());
        schedule.setUpdatedAt(LocalDateTime.now());

        return schedulesRepository.save(schedule);

    }

    @Override
    public List<Schedules> getAllSchedules() {
        return schedulesRepository.findAll();
        }

    @Override
    public Optional<Schedules> getScheduleById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Schedule ID cannot be null");
        }
        return schedulesRepository.findById(id);
    }

    @Override
    public List<Schedules> getSchedulesByStationId(Long stationId) {
        if (stationId == null) {
            throw new IllegalArgumentException("Station ID cannot be null");
        }
        return schedulesRepository.findByStationStationIdOrderByTimeArrival(stationId);
    }

    @Override
    public Schedules updateSchedule(Long id, Schedules scheduleUpdate) {
        if (id == null) {
            throw new IllegalArgumentException("Schedule ID cannot be null");
        }

        Schedules existingSchedule = schedulesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found with id: " + id));

        if (scheduleUpdate.getDescription() != null) {
            existingSchedule.setDescription(scheduleUpdate.getDescription().trim());
        }

        if (scheduleUpdate.getTimeArrival() != null) {
            existingSchedule.setTimeArrival(scheduleUpdate.getTimeArrival());
        }

        if (scheduleUpdate.getTimeDeparture() != null) {
            existingSchedule.setTimeDeparture(scheduleUpdate.getTimeDeparture());
        }
        if (existingSchedule.getTimeArrival() != null && existingSchedule.getTimeDeparture() != null &&
                existingSchedule.getTimeArrival().isAfter(existingSchedule.getTimeDeparture())) {
            throw new RuntimeException("Arrival time cannot be after departure time");
        }

        if (scheduleUpdate.getStation() != null && scheduleUpdate.getStation().getStationId() != null) {
            Stations station = stationsRepository.findById(scheduleUpdate.getStation().getStationId())
                    .orElseThrow(() -> new RuntimeException("Station not found with id: " + scheduleUpdate.getStation().getStationId()));
            existingSchedule.setStation(station);
        }

        if (scheduleUpdate.getDirection() != null) {
            existingSchedule.setDirection(scheduleUpdate.getDirection());
        }

        existingSchedule.setUpdatedAt(LocalDateTime.now());

        return schedulesRepository.save(existingSchedule);
    }

    @Override
    public void deleteSchedule(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Schedule ID cannot be null");
        }

        if (!schedulesRepository.existsById(id)) {
            throw new RuntimeException("Schedule not found with id: " + id);
        }

        schedulesRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        if (id == null) {
            return false;
        }
        return schedulesRepository.existsById(id);
    }
}
