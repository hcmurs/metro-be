package com.example.stationservice.repository;

import com.example.stationservice.entity.Schedules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchedulesRepository extends JpaRepository<Schedules, Integer> {
    //List<Schedules> findByStationId(Integer stationId);
}
