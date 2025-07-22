package com.example.stationservice.repository;

import com.example.stationservice.model.Schedules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchedulesRepository extends JpaRepository<Schedules, Long> {
    List<Schedules> findByStationRouteId(Long stationRouteId);
    List<Schedules> findByStationRoute_IdOrderByTimeArrival(Long stationStationId);
    //List<Schedules> findByStationId(Integer stationId);

}
