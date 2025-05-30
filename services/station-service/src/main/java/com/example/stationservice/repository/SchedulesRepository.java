package com.example.stationservice.repository;

import com.example.stationservice.model.Schedules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchedulesRepository extends JpaRepository<Schedules, Integer> {
    //List<Schedules> findByStationId(Integer stationId);
}
