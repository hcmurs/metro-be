package com.example.stationservice.repository;

import com.example.stationservice.model.Stations;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationsRepository extends JpaRepository<Stations, Long> {
    List<Stations> findByNameContainingIgnoreCase(String name);
    Boolean  existsByStationCode (String stationCode);
     List<Stations> findByRouteRouteIdOrderBySequenceOrder (Long routeId);
     boolean existsBySequenceOrder (Integer sequence);
    }
