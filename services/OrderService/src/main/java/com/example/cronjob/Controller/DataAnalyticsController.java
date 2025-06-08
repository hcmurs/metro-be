package com.example.cronjob.Controller;

import com.example.cronjob.DTO.Response.DailyAnalyticsResponse;
import com.example.cronjob.Service.DailyAnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class DataAnalyticsController {

    @Autowired
    private DailyAnalyticsService dailyAnalyticsService;

    @GetMapping("/get-data")
    public ResponseEntity<List<DailyAnalyticsResponse>> getDaily() {
        return ResponseEntity.ok(dailyAnalyticsService.getDaily());
    }
}
