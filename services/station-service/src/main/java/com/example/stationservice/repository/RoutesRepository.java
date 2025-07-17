package com.example.stationservice.repository;

import com.example.stationservice.model.Routes;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoutesRepository extends JpaRepository<Routes, Long> {
    // Additional query methods can be defined here if needed
    Boolean   existsByRouteCode(String routeCode);
    List<Routes> findByRouteNameContainingIgnoreCase (String name);
    Optional<Routes> findByRouteCode(String routeCode);
}
