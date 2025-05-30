package com.example.stationservice.repository;

import com.example.stationservice.model.Routes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoutesRepository extends JpaRepository<Routes, Integer> {
    // Additional query methods can be defined here if needed
}
