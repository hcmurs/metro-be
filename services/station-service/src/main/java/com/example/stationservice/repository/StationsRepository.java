package com.example.stationservice.repository;

import com.example.stationservice.entity.Stations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StationsRepository extends JpaRepository<Stations, Integer> {
    List<Stations> findByRouteId(Integer routeId);
    List<Stations> findByStationCode(Integer stationCode);
    List<Stations> findByNameContainingIgnoreCase(String name);
    List<Stations> findByStatus(Stations.Status status);
    }
