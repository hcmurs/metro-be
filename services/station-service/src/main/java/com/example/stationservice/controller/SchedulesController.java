package com.example.stationservice.controller;

import com.example.stationservice.config.ApiResponse;
import com.example.stationservice.dto.SchedulesRequest;
import com.example.stationservice.dto.SchedulesResponse;
import com.example.stationservice.model.Schedules;
import com.example.stationservice.service.SchedulesService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/schedules")
@Tag(name = "Schedules", description = "Operations related to schedules")
public class SchedulesController {

    @Autowired
    private SchedulesService schedulesService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ApiResponse<SchedulesResponse> createSchedule(@RequestBody SchedulesRequest request) {
        SchedulesResponse schedule = schedulesService.createSchedule(request);
        return ApiResponse.success(schedule, "Schedule created successfully");
    }

    @GetMapping
    public ApiResponse<List<SchedulesResponse>> getAllSchedules() {
        List<SchedulesResponse> schedules = schedulesService.getAllSchedules();
        return ApiResponse.success(schedules, "Schedules retrieved successfully");
    }

    @GetMapping("/{id}")
    public ApiResponse<SchedulesResponse> getScheduleById(@PathVariable Long id) {
        Optional<SchedulesResponse> schedule = schedulesService.getScheduleById(id);
        if (schedule.isPresent()) {
            return ApiResponse.success(schedule.get(), "Schedule found");
        } else {
            throw new IllegalArgumentException("Schedule not found with id: " + id);
        }
    }

    @GetMapping("/station/{stationId}")
    public ApiResponse<List<SchedulesResponse>> getSchedulesByStationId(@PathVariable Long stationId) {
        List<SchedulesResponse> schedules = schedulesService.getSchedulesByStationId(stationId);
        return ApiResponse.success(schedules, "Schedules found for station");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ApiResponse<SchedulesResponse> updateSchedule(@PathVariable Long id, @RequestBody SchedulesRequest schedule) {
        SchedulesResponse updatedSchedule = schedulesService.updateSchedule(id, schedule);
        return ApiResponse.success(updatedSchedule, "Schedule updated successfully");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
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