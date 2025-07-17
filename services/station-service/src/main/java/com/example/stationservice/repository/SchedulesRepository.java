package com.example.stationservice.repository;

import com.example.stationservice.model.Schedules;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchedulesRepository extends JpaRepository<Schedules, Long> {
    List<Schedules> findByStationStationIdOrderByTimeArrival(Long stationId);
    //List<Schedules> findByStationId(Integer stationId);
}
