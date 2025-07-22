package com.example.stationservice.service;

import com.example.stationservice.dto.SchedulesRequest;
import com.example.stationservice.dto.SchedulesResponse;
import com.example.stationservice.model.Schedules;
import com.example.stationservice.model.StationRoute;
import com.example.stationservice.model.Stations;
import com.example.stationservice.repository.SchedulesRepository;
import com.example.stationservice.repository.StationRouteRepository;
import com.example.stationservice.repository.StationsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class SchedulesServiceImpl implements SchedulesService {
    @Autowired
    private SchedulesRepository schedulesRepository;

    @Autowired
    private StationRouteRepository stationRouteRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Transactional
    @Override
    public SchedulesResponse createSchedule(SchedulesRequest request) {
        StationRoute stationRoute = stationRouteRepository.findById(request.getStationId())
                .orElseThrow(() -> new EntityNotFoundException("Station not found with id: " + request.getStationId()));
        if (request.getTimeArrival() != null && request.getTimeDeparture() != null &&
                request.getTimeArrival().isAfter(request.getTimeDeparture())) {
            throw new RuntimeException("Arrival time cannot be after departure time");
        }
        Schedules schedule = new Schedules();
        schedule.setDescription(request.getDescription());
        schedule.setTimeArrival(request.getTimeArrival());
        schedule.setTimeDeparture(request.getTimeDeparture());
        schedule.setStationRoute(stationRoute);
        schedule.setDirection(request.getDirection());
        schedule.setCreatedAt(LocalDateTime.now());
        schedule.setUpdatedAt(LocalDateTime.now());

         schedulesRepository.save(schedule);
         return modelMapper.map(schedule, SchedulesResponse.class);

    }

    @Override
    public List<SchedulesResponse> getAllSchedules() {
        List<Schedules> list=  schedulesRepository.findAll();
        return list.stream()
                .map(schedule -> modelMapper.map(schedule, SchedulesResponse.class))
                .toList();
        }

    @Override
    public Optional<SchedulesResponse> getScheduleById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Schedule ID cannot be null");
        }
        Optional<Schedules> optional =  schedulesRepository.findById(id);
        return optional.map(schedule -> modelMapper.map(schedule, SchedulesResponse.class));
    }

    @Override
    public List<SchedulesResponse> getSchedulesByStationId(Long stationId) {
        if (stationId == null) {
            throw new IllegalArgumentException("Station ID cannot be null");
        }
        List<Schedules> list = schedulesRepository.findByStationRoute_IdOrderByTimeArrival(stationId);
        return list.stream()
                .map(schedule -> modelMapper.map(schedule, SchedulesResponse.class))
                .toList();
    }
    @Transactional
    @Override
    public SchedulesResponse updateSchedule(Long id, SchedulesRequest scheduleUpdate) {
        if (id == null) {
            throw new IllegalArgumentException("Schedule ID cannot be null");
        }

        Schedules existingSchedule = schedulesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found with id: " + id));

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

        if (scheduleUpdate.getStationId() != null &&
                (existingSchedule.getStationRoute().getId().equals(scheduleUpdate.getStationId()))) {
            StationRoute station = stationRouteRepository.findById(scheduleUpdate.getStationId())
                    .orElseThrow(() -> new EntityNotFoundException("Station not found with id: " + scheduleUpdate.getStationId()));
            existingSchedule.setStationRoute(station);
        }

        if (scheduleUpdate.getDirection() != null) {
            existingSchedule.setDirection(scheduleUpdate.getDirection());
        }

        existingSchedule.setUpdatedAt(LocalDateTime.now());

         schedulesRepository.save(existingSchedule);
         return modelMapper.map(existingSchedule, SchedulesResponse.class);
    }
    @Transactional
    @Override
    public void deleteSchedule(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Schedule ID cannot be null");
        }

        if (!schedulesRepository.existsById(id)) {
            throw new EntityNotFoundException("Schedule not found with id: " + id);
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
