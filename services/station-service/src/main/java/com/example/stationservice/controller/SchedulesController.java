package com.example.stationservice.controller;

import com.example.stationservice.config.ApiResponse;
import com.example.stationservice.dto.SchedulesRequest;
import com.example.stationservice.model.Schedules;
import com.example.stationservice.service.SchedulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/schedules")
@CrossOrigin(origins = "*")
public class SchedulesController {

    @Autowired
    private SchedulesService schedulesService;

    @PostMapping
    public ApiResponse<Schedules> createSchedule(@RequestBody SchedulesRequest request) {
        Schedules schedule = schedulesService.createSchedule(request);
        return ApiResponse.success(schedule, "Schedule created successfully");
    }

    @GetMapping
    public ApiResponse<List<Schedules>> getAllSchedules() {
        List<Schedules> schedules = schedulesService.getAllSchedules();
        return ApiResponse.success(schedules, "Schedules retrieved successfully");
    }

    @GetMapping("/{id}")
    public ApiResponse<Schedules> getScheduleById(@PathVariable Long id) {
        Optional<Schedules> schedule = schedulesService.getScheduleById(id);
        if (schedule.isPresent()) {
            return ApiResponse.success(schedule.get(), "Schedule found");
        } else {
            throw new IllegalArgumentException("Schedule not found with id: " + id);
        }
    }

    @GetMapping("/station/{stationId}")
    public ApiResponse<List<Schedules>> getSchedulesByStationId(@PathVariable Long stationId) {
        List<Schedules> schedules = schedulesService.getSchedulesByStationId(stationId);
        return ApiResponse.success(schedules, "Schedules found for station");
    }

    @PutMapping("/{id}")
    public ApiResponse<Schedules> updateSchedule(@PathVariable Long id, @RequestBody Schedules schedule) {
        Schedules updatedSchedule = schedulesService.updateSchedule(id, schedule);
        return ApiResponse.success(updatedSchedule, "Schedule updated successfully");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteSchedule(@PathVariable Long id) {
        schedulesService.deleteSchedule(id);
        return ApiResponse.success("Schedule deleted successfully");
    }

    @GetMapping("/{id}/exists")
    public ApiResponse<Boolean> checkScheduleExists(@PathVariable Long id) {
        boolean exists = schedulesService.existsById(id);
        return ApiResponse.success(exists, "Schedule existence checked");
    }

}