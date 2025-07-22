package com.example.stationservice.repository;

import com.example.stationservice.model.Stations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StationsRepository extends JpaRepository<Stations, Long> {
    List<Stations> findByNameContainingIgnoreCase(String name);
    Boolean  existsByStationCode (String stationCode);
    List<Stations> findAllByIsDeletedFalse();
    }
